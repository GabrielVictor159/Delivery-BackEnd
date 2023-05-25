package com.Delivery.delivery.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Delivery.delivery.model.Categoria;
import com.Delivery.delivery.model.Produto;
import com.Delivery.delivery.repository.PedidoProdutoRepository;
import com.Delivery.delivery.repository.ProdutoRepository;
import org.springframework.data.domain.Sort;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    PedidoProdutoRepository pedidoProdutoRepository;

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }

    public Page<Produto> buscarProdutosPaginados(String nome, String categoria, double precoMinimo, double precoMaximo,
            String descricao, int pagina, int tamanhoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        System.out.println(pageable);
        if (categoria != null && nome != null) {
            return produtoRepository
                    .findAllByNomeContainingIgnoreCaseAndCategoria_NomeAndPrecoBetweenAndDescricaoContainingIgnoreCase(
                            nome, categoria, precoMinimo == -1 ? 0 : precoMinimo,
                            precoMaximo == -1 ? Double.MAX_VALUE : precoMaximo, descricao, pageable);
        } else if (categoria != null) {
            return produtoRepository.findAllByCategoria_NomeAndPrecoBetweenAndDescricaoContainingIgnoreCase(
                    categoria, precoMinimo == -1 ? 0 : precoMinimo, precoMaximo == -1 ? Double.MAX_VALUE : precoMaximo,
                    descricao, pageable);
        } else if (nome != null) {
            return produtoRepository.findAllByNomeContainingIgnoreCaseAndPrecoBetweenAndDescricaoContainingIgnoreCase(
                    nome, precoMinimo == -1 ? 0 : precoMinimo, precoMaximo == -1 ? Double.MAX_VALUE : precoMaximo,
                    descricao, pageable);
        } else if (descricao != null) {
            return produtoRepository.findAllByPrecoBetweenAndDescricaoContainingIgnoreCase(
                    precoMinimo == -1 ? 0 : precoMinimo, precoMaximo == -1 ? Double.MAX_VALUE : precoMaximo, descricao,
                    pageable);
        } else if (precoMinimo != -1 && precoMaximo != -1) {
            return produtoRepository.findAllByPrecoBetween(
                    precoMinimo, precoMaximo, pageable);
        } else {
            return produtoRepository.findAll(pageable);
        }
    }

    public List<Produto> buscarProdutosPorIds(List<UUID> ids) {
        return produtoRepository.findAllByIdIn(ids);
    }

    public Optional<Produto> buscarPorId(UUID id) {
        return produtoRepository.findById(id);
    }

    public void deletar(UUID id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            try {
                pedidoProdutoRepository.deleteAllByProduto(produto.get());
                produtoRepository.deleteById(id);
            } catch (Exception e) {
            }
        }
    }

    public void deletarAllByCategoria(Categoria categoria) {
        List<Produto> listProduto = produtoRepository.findAllByCategoria(categoria);
        for (Produto produto : listProduto) {
            try {
                pedidoProdutoRepository.deleteAllByProduto(produto);
            } catch (Exception e) {
            }
        }
        produtoRepository.deleteAllByCategoria(categoria);

    }
}
