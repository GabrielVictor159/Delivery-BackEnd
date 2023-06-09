package com.Delivery.delivery.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Delivery.delivery.config.SystemConfig;
import com.Delivery.delivery.dto.ProdutoDTO;
import com.Delivery.delivery.dto.genericDTOs.PaginacaoProdutoDTO;
import com.Delivery.delivery.functions.DecodeURLComponent;
import com.Delivery.delivery.model.Admin;
import com.Delivery.delivery.model.Categoria;
import com.Delivery.delivery.model.Produto;
import com.Delivery.delivery.service.AdminService;
import com.Delivery.delivery.service.CategoriaService;
import com.Delivery.delivery.service.ImageService;
import com.Delivery.delivery.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/Produtos", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin
public class ProdutoController {
    @Autowired
    ProdutoService produtoService;
    @Autowired
    CategoriaService categoriaService;
    @Autowired
    AdminService adminService;
    @Autowired
    ImageService imageService;

    @PostMapping("/getAll")
    public ResponseEntity<Object> buscarProdutosPaginados(@RequestBody @Valid PaginacaoProdutoDTO paginacaoDTO) {
        try {
            String categoria = paginacaoDTO.getCategoria().orElse("");
            String nome = paginacaoDTO.getNome().orElse("");
            double precoMinimo = paginacaoDTO.getPrecoMinimo();
            double precoMaximo = paginacaoDTO.getPrecoMaximo();
            String descricao = paginacaoDTO.getDescricao().orElse("");
            int pagina = paginacaoDTO.getPagina();
            int tamanhoPagina = paginacaoDTO.getTamanhoPagina();
            Page<Produto> produtos = produtoService.buscarProdutosPaginados(nome,
                    categoria, precoMinimo, precoMaximo,
                    descricao, pagina - 1, tamanhoPagina);
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Houve um erro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarProdutoPorId(@PathVariable UUID id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        if (produto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(produto.get(), HttpStatus.OK);
        }
    }

    @PostMapping("getByIds")
    public ResponseEntity<Object> getByIds(@RequestBody List<UUID> ids) {
        try {
            return new ResponseEntity<>(produtoService.buscarProdutosPorIds(ids), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Houve um erro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };

    @PostMapping("/{nomeAdmin}/{senhaAdmin}")
    public ResponseEntity<Object> adicionar(@PathVariable String nomeAdmin, @PathVariable String senhaAdmin,
            @RequestBody ProdutoDTO dto) {
        Optional<Admin> admin = adminService.login(
                SystemConfig.AdminURLEncoder ? DecodeURLComponent.decodeURLComponent(nomeAdmin) : nomeAdmin,
                SystemConfig.AdminURLEncoder ? DecodeURLComponent.decodeURLComponent(senhaAdmin) : senhaAdmin);
        if (admin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            try {
                Optional<Categoria> categoria = categoriaService.buscarPorId(dto.getIdCategoria());
                if (categoria.isPresent()) {
                    Produto produto = new Produto();
                    produto.setCategoria(categoria.get());
                    BeanUtils.copyProperties(dto, produto);
                    String[] imagens = adicionarImagens(dto.getImagens());
                    produto.setImagens(Arrays.toString(imagens));
                    produtoService.salvar(produto);
                    return new ResponseEntity<>(produto, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("O id da categoria não foi encontrado", HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>("Houve um erro", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PutMapping("/{id}/{nomeAdmin}/{senhaAdmin}")
    public ResponseEntity<Object> atualizar(@PathVariable UUID id, @PathVariable String nomeAdmin,
            @PathVariable String senhaAdmin, @RequestBody ProdutoDTO dto) {
        Optional<Admin> admin = adminService.login(
                SystemConfig.AdminURLEncoder ? DecodeURLComponent.decodeURLComponent(nomeAdmin) : nomeAdmin,
                SystemConfig.AdminURLEncoder ? DecodeURLComponent.decodeURLComponent(senhaAdmin) : senhaAdmin);
        if (admin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            try {
                Optional<Produto> produtoOptional = produtoService.buscarPorId(id);
                Optional<Categoria> categoria = categoriaService.buscarPorId(dto.getIdCategoria());
                if (produtoOptional.isPresent()) {
                    if (categoria.isPresent()) {
                        Produto produto = produtoOptional.get();
                        produto.setCategoria(categoria.get());
                        excluirImagens(produto.getImagens());
                        String[] imagens = adicionarImagens(dto.getImagens());
                        produto.setImagens(Arrays.toString(imagens));
                        BeanUtils.copyProperties(dto, produto);
                        produtoService.salvar(produto);
                        return new ResponseEntity<>(produto, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("O id da categoria não foi encontrado", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>("O produto original não existe", HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>("Houve um erro", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping("/{id}/{nomeAdmin}/{senhaAdmin}")
    public ResponseEntity<Object> Deletar(@PathVariable UUID id, @PathVariable String nomeAdmin,
            @PathVariable String senhaAdmin) {
        Optional<Admin> admin = adminService.login(
                SystemConfig.AdminURLEncoder ? DecodeURLComponent.decodeURLComponent(nomeAdmin) : nomeAdmin,
                SystemConfig.AdminURLEncoder ? DecodeURLComponent.decodeURLComponent(senhaAdmin) : senhaAdmin);
        if (admin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            Optional<Produto> produtoOptional = produtoService.buscarPorId(id);
            if (produtoOptional.isPresent()) {
                try {
                    excluirImagens(produtoOptional.get().getImagens());
                    produtoService.deletar(id);
                    return new ResponseEntity<>(HttpStatus.OK);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>("Houve um erro", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    private void excluirImagens(String imagens) {
        if (imagens != null && !imagens.isEmpty()) {
            String[] imagensArray = imagens.split(", ");
            for (String imagePath : imagensArray) {
                try {
                    imageService.excluir(imagePath.trim());
                } catch (Exception e) {
                    System.out.println("Erro ao excluir a imagem: " + imagePath);
                    e.printStackTrace();
                }
            }
        }
    }

    private String[] adicionarImagens(byte[][] imagensDto) {
        String[] imagens = new String[imagensDto.length];
        for (int i = 0; i < imagensDto.length; i++) {
            byte[] image = imagensDto[i];
            try {
                String imagePath = imageService.salvar(image);
                imagens[i] = imagePath;
            } catch (Exception e) {
                System.out.println("Erro ao adicionar a imagem");
                e.printStackTrace();
            }
        }
        return imagens;
    }
}
