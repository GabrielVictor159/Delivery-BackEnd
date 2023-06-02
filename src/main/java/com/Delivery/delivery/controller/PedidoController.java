package com.Delivery.delivery.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Delivery.delivery.dto.PedidoDTO;
import com.Delivery.delivery.dto.genericDTOs.PedidoRetorno;
import com.Delivery.delivery.dto.genericDTOs.PedidosRetornoDono;
import com.Delivery.delivery.dto.genericDTOs.PedidosRetornoFuncionario;
import com.Delivery.delivery.dto.genericDTOs.ProdutoRetorno;
import com.Delivery.delivery.dto.genericDTOs.listProducts;
import com.Delivery.delivery.functions.DecodeURLComponent;
import com.Delivery.delivery.functions.ValidationMethods;
import com.Delivery.delivery.Exceptions.InvalidRegionException;
import com.Delivery.delivery.config.SystemConfig;
import com.Delivery.delivery.model.Admin;
import com.Delivery.delivery.model.IpPerson;
import com.Delivery.delivery.model.Pedido;
import com.Delivery.delivery.model.PedidoProduto;
import com.Delivery.delivery.model.Produto;
import com.Delivery.delivery.model.Enums.typeAdmins;
import com.Delivery.delivery.service.AdminService;
import com.Delivery.delivery.service.IpPersonService;
import com.Delivery.delivery.service.PedidoProdutoService;
import com.Delivery.delivery.service.PedidosService;
import com.Delivery.delivery.service.ProdutoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

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
        Optional<Admin> admin = adminService.login(
                SystemConfig.AdminURLEncoder ? DecodeURLComponent.decodeURLComponent(nomeAdmin) : nomeAdmin,
                SystemConfig.AdminURLEncoder ? DecodeURLComponent.decodeURLComponent(senhaAdmin) : senhaAdmin);
        if (admin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Page<Pedido> pedidos = pedidosService.buscarPedidosPaginados(pagina, tamanhoPagina);
        if (admin.get().getTipo() == typeAdmins.DONO) {
            List<PedidosRetornoDono> retorno = new ArrayList<>();
            for (Pedido pedido : pedidos) {
                PedidosRetornoDono produtoRetorno = new PedidosRetornoDono();
                BeanUtils.copyProperties(pedido, produtoRetorno);
                produtoRetorno.setProdutos(retorneProdutos(pedido));
                retorno.add(produtoRetorno);
            }
            return new ResponseEntity<>(retorno, HttpStatus.OK);
        } else {
            List<PedidosRetornoFuncionario> retorno = new ArrayList<>();
            for (Pedido pedido : pedidos) {
                PedidosRetornoFuncionario produtoRetorno = new PedidosRetornoFuncionario();
                BeanUtils.copyProperties(pedido, produtoRetorno);
                produtoRetorno.setProdutos(retorneProdutos(pedido));
                produtoRetorno.setIp(pedido.getIpPerson().getIpCode());
                retorno.add(produtoRetorno);
            }
            return new ResponseEntity<>(retorno, HttpStatus.OK);
        }
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
    public ResponseEntity<Object> adicionarPedido(@RequestBody @Valid PedidoDTO pedidoDto, HttpServletRequest request) {
        List<UUID> listIds = new ArrayList<>();
        for (listProducts a : pedidoDto.getProdutos()) {
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
            for (listProducts b : pedidoDto.getProdutos()) {
                if (b.getId().equals(a.getId())) {
                    valorTotal += a.getPreco() * b.getQuantidade();
                }
            }

        }
        if (!ValidationMethods.isCPF(pedidoDto.getCpf())) {
            return new ResponseEntity<>("CPF invalido", HttpStatus.BAD_REQUEST);
        }
        CompletableFuture<Boolean> cepValidoFuture = ValidationMethods
                .isValidCEPAsync(Integer.parseInt(pedidoDto.getCep()));
        try {
            if (!cepValidoFuture.get()) {
                return new ResponseEntity<>("CEP invalido", HttpStatus.BAD_REQUEST);
            }
            String ip = request.getRemoteAddr();
            try {
                IpPerson ipPerson = null;
                Optional<IpPerson> ipCadastroBusca = ipPersonService.findByIpCode(ip);

                if (ipCadastroBusca.isEmpty()) {
                    IpPerson ipVerification = new IpPerson();
                    CompletableFuture<IpPerson> ipPersonVerification = ValidationMethods.getIpPerson(ip);

                    if (ipPersonVerification != null && ipPersonVerification.isDone()) {
                        ipPersonVerification.get().setIpCode(ip);
                        ipVerification = ipPersonVerification.get();
                    } else {
                        ipVerification.setIpCode(ip);
                    }

                    System.out.println("ip = " + ip + " ipVerification = " + ipVerification);
                    ipPerson = ipPersonService.save(ipVerification);
                } else {
                    IpPerson ipCadastro = ipCadastroBusca.get();

                    if (ipCadastro.getBanned()) {
                        return new ResponseEntity<>(
                                "Você está banido de utilizar nosso sistema",
                                HttpStatus.UNAUTHORIZED);
                    }

                    ipPerson = ipCadastro;
                }

                try {
                    Pedido pedido = new Pedido();
                    BeanUtils.copyProperties(pedidoDto, pedido);
                    try {
                        pedido.setIpPerson(ipCadastroBusca.isEmpty() ? ipPerson : ipCadastroBusca.get());
                        pedido.setValorTotal(valorTotal);
                        pedido.setMetodoPagamento(pedidoDto.getMetodoPagamento().toString());
                        Pedido pedidoSalvo = pedidosService.salvar(pedido);
                        try {
                            List<PedidoProduto> listPedidoProduto = new ArrayList<>();
                            for (listProducts a : pedidoDto.getProdutos()) {
                                for (Produto b : listProdutos) {
                                    if (b.getId().equals(a.getId())) {
                                        PedidoProduto pedidoProduto = new PedidoProduto(pedidoSalvo, b,
                                                a.getQuantidade());
                                        listPedidoProduto.add(pedidoProduto);
                                    }
                                }
                            }
                            pedidoProdutoService.salvarTodos(listPedidoProduto);
                            PedidoRetorno retorno = new PedidoRetorno();
                            retorno.setProdutos(retorneProdutos(pedidoSalvo));
                            retorno.setId(pedidoSalvo.getId());
                            retorno.setMetodoPagamento(pedidoSalvo.getMetodoPagamento());
                            retorno.setValorTotal(pedidoSalvo.getValorTotal());
                            return new ResponseEntity<>(retorno, HttpStatus.OK);
                        } catch (Exception e) {
                            System.out.println("\n \n \n Houve um erro: " + e + " \n \n \n");
                            pedidosService.deletar(pedidoSalvo.getId());
                            if (ipCadastroBusca.isEmpty()) {
                                ipPersonService.deletar(ipPerson.getId());
                            }
                            return new ResponseEntity<>(
                                    "Houve um erro ao tentar salvar a relação entre o seu pedido e os produtos",
                                    HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    } catch (Exception e) {
                        System.out.println("\n \n \n Houve um erro: " + e + " \n \n \n");
                        if (ipCadastroBusca.isEmpty()) {
                            ipPersonService.deletar(ipPerson.getId());
                        }
                        return new ResponseEntity<>(
                                "Houve um erro ao tentar salvar o seu pedido",
                                HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                } catch (Exception e) {
                    System.out.println("\n \n \n Houve um erro: " + e + " \n \n \n");
                    return new ResponseEntity<>(
                            "Houve um erro ao tentar salvar os dados de ip, por isso não salvamos o pedido",
                            HttpStatus.INTERNAL_SERVER_ERROR);
                }
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

    @PutMapping("/BanirIp/{id}/{nomeAdmin}/{senhaAdmin}")
    public ResponseEntity<Object> banirIp(@PathVariable UUID id, @PathVariable String nomeAdmin,
            @PathVariable String senhaAdmin) {
        Optional<Admin> admin = adminService.login(
                SystemConfig.AdminURLEncoder ? DecodeURLComponent.decodeURLComponent(nomeAdmin) : nomeAdmin,
                SystemConfig.AdminURLEncoder ? DecodeURLComponent.decodeURLComponent(senhaAdmin) : senhaAdmin);
        try {
            if (admin.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
                Optional<IpPerson> ipPerson = ipPersonService.findOne(id);
                if (ipPerson.isPresent()) {
                    ipPerson.get().setBanned(true);
                    ipPersonService.save(ipPerson.get());
                    return new ResponseEntity<>("Ip banido", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Id de ip invalido", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            System.out.println("\n \n \n Houve um erro: " + e + " \n \n \n");
            return new ResponseEntity<>("Houve um erro no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
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
