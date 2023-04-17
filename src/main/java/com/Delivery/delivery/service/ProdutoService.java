package com.Delivery.delivery.service;

import java.util.List;
import java.util.NoSuchElementException;
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

    public Page<Produto> buscarProdutosPaginados(int pagina, int tamanhoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        return produtoRepository.findAll(pageable);
    }

    public Page<Produto> buscarProdutosPaginadosPorNome(String nome, int pagina, int tamanhoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        return produtoRepository.findAllByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Produto> buscarProdutosPaginadosPorCategoria(Categoria categoria, int pagina, int tamanhoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        return produtoRepository.findAllByCategoria(categoria, pageable);
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
