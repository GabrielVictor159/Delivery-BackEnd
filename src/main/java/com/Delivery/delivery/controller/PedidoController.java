package com.Delivery.delivery.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Delivery.delivery.dto.PedidoDTO;
import com.Delivery.delivery.dto.genericDTOs.PedidoRetorno;
import com.Delivery.delivery.dto.genericDTOs.ProdutoRetorno;
import com.Delivery.delivery.dto.genericDTOs.listProducts;
import com.Delivery.delivery.functions.ValidationMethods;
import com.Delivery.delivery.Exceptions.InvalidRegionException;
import com.Delivery.delivery.model.Admin;
import com.Delivery.delivery.model.IpPerson;
import com.Delivery.delivery.model.Pedido;
import com.Delivery.delivery.model.PedidoProduto;
import com.Delivery.delivery.model.Produto;
import com.Delivery.delivery.service.AdminService;
import com.Delivery.delivery.service.IpPersonService;
import com.Delivery.delivery.service.PedidoProdutoService;
import com.Delivery.delivery.service.PedidosService;
import com.Delivery.delivery.service.ProdutoService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/Pedidos", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin
public class PedidoController {

    @Autowired
    PedidosService pedidosService;
    @Autowired
    PedidoProdutoService pedidoProdutoService;
    @Autowired
    AdminService adminService;
    @Autowired
    IpPersonService ipPersonService;
    @Autowired
    ProdutoService produtoService;

    @GetMapping("/{nomeAdmin}/{senhaAdmin}")
    public ResponseEntity<Object> buscarPedidosPaginados(
            @PathVariable String nomeAdmin,
            @PathVariable String senhaAdmin,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanhoPagina) {
        Optional<Admin> admin = adminService.login(nomeAdmin, senhaAdmin);
        if (admin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Page<Pedido> pedidos = pedidosService.buscarPedidosPaginados(pagina, tamanhoPagina);
        List<PedidoRetorno> retorno = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            PedidoRetorno produtoRetorno = new PedidoRetorno();
            BeanUtils.copyProperties(pedido, produtoRetorno);
            produtoRetorno.setProdutos(retorneProdutos(pedido));
            retorno.add(produtoRetorno);
        }
        return new ResponseEntity<>(retorno, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPedido(@PathVariable UUID id) {
        Optional<Pedido> pedido = pedidosService.buscarPorId(id);
        if (pedido.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PedidoRetorno retorno = new PedidoRetorno();

        retorno.setProdutos(retorneProdutos(pedido.get()));
        return new ResponseEntity<Object>(retorno, HttpStatus.OK);
    }

    @PostMapping()
    @Async
    public ResponseEntity<Object> adicionarPedido(@RequestParam @Valid PedidoDTO pedidoDto,
            @RequestParam List<listProducts> produtos, HttpServletRequest request) {
        List<UUID> listIds = new ArrayList<>();
        for (listProducts a : produtos) {
            listIds.add(a.getId());
        }
        List<Produto> listProdutos = produtoService.buscarProdutosPorIds(listIds);
        if (listProdutos.isEmpty()) {
            return new ResponseEntity<>("Adicione pelo menos um item de produtos", HttpStatus.BAD_REQUEST);
        }
        if (!ValidationMethods.validarTelefone(pedidoDto.getTelefone())) {
            return new ResponseEntity<>("Telefone invalido", HttpStatus.BAD_REQUEST);
        }
        Double valorTotal = 0.00;
        for (Produto a : listProdutos) {
            for (listProducts b : produtos) {
                if (b.getId() == a.getId()) {
                    valorTotal += a.getPreco() * b.getQuantidade();
                }
            }

        }
        if (!ValidationMethods.isCPF(pedidoDto.getCpf())) {
            return new ResponseEntity<>("CPF invalido", HttpStatus.BAD_REQUEST);
        }
        CompletableFuture<Boolean> cepValidoFuture = ValidationMethods.isValidCEPAsync(pedidoDto.getCep());
        try {
            if (!cepValidoFuture.get()) {
                return new ResponseEntity<>("CEP invalido", HttpStatus.BAD_REQUEST);
            }
            String ip = request.getRemoteAddr();
            try {
                CompletableFuture<IpPerson> ipPersonVerification = ValidationMethods.getIpPerson(ip);
                try {
                    IpPerson ipPerson = ipPersonService.save(ipPersonVerification.get());
                    Pedido pedido = new Pedido();
                    BeanUtils.copyProperties(pedidoDto, pedido);
                    try {
                        pedido.setIpPerson(ipPerson);
                        Pedido pedidoSalvo = pedidosService.salvar(pedido);
                        try {
                            List<PedidoProduto> listPedidoProduto = new ArrayList<>();
                            for (listProducts a : produtos) {
                                for (Produto b : listProdutos) {
                                    if (a.getId() == b.getId()) {
                                        PedidoProduto pedidoProduto = new PedidoProduto(pedido, b, a.getQuantidade());
                                        listPedidoProduto.add(pedidoProduto);
                                    }
                                }
                            }
                            pedidoProdutoService.salvarTodos(listPedidoProduto);
                            PedidoRetorno retorno = new PedidoRetorno();
                            retorno.setProdutos(retorneProdutos(pedidoSalvo));
                            return new ResponseEntity<>(retorno, HttpStatus.OK);
                        } catch (Exception e) {
                            System.out.println("\n \n \n Houve um erro: " + e.getMessage() + " \n \n \n");
                            pedidosService.deletar(pedidoSalvo.getId());
                            ipPersonService.deletar(ipPerson.getId());
                            return new ResponseEntity<>(
                                    "Houve um erro ao tentar salvar a relação entre o seu pedido e os produtos",
                                    HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    } catch (Exception e) {
                        System.out.println("\n \n \n Houve um erro: " + e.getMessage() + " \n \n \n");
                        ipPersonService.deletar(ipPerson.getId());
                        return new ResponseEntity<>(
                                "Houve um erro ao tentar salvar o seu pedido",
                                HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                } catch (Exception e) {
                    System.out.println("\n \n \n Houve um erro: " + e.getMessage() + " \n \n \n");
                    return new ResponseEntity<>(
                            "Houve um erro ao tentar salvar os dados de ip, por isso não salvamos o pedido",
                            HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (InvalidRegionException e) {
                System.out.println("\n \n \n Houve um erro: " + e + " \n \n \n");
                return new ResponseEntity<>(
                        "O seu Ip não esta registrado no DF portanto verifique se você se encontra no df ou se esta usando um vpn",
                        HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                System.out.println("\n \n \n Houve um erro: " + e + " \n \n \n");
                return new ResponseEntity<Object>("Houve um erro ao validar o seu ip",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao validar CEP", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExecutionException e) {
            return new ResponseEntity<>("Erro ao validar CEP", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<ProdutoRetorno> retorneProdutos(Pedido pedido) {
        List<PedidoProduto> produtosRetorno = pedidoProdutoService.buscarPorPedido(pedido);
        List<ProdutoRetorno> produtos = new ArrayList<>();
        for (PedidoProduto a : produtosRetorno) {
            ProdutoRetorno b = new ProdutoRetorno();
            BeanUtils.copyProperties(a.getProduto(), b);
            b.setQuantidade(a.getQuantidade());
            produtos.add(b);
        }
        return produtos;
    }
}
