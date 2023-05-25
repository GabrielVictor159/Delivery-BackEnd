package com.Delivery.delivery.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Delivery.delivery.model.Pedido;
import com.Delivery.delivery.model.PedidoProduto;
import com.Delivery.delivery.model.Produto;
import com.Delivery.delivery.repository.PedidoProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoProdutoService {
    @Autowired
    PedidoProdutoRepository pedidoProdutoRepository;

    @Transactional
    public PedidoProduto salvar(PedidoProduto pedidoProduto) {
        return pedidoProdutoRepository.save(pedidoProduto);
    }

    @Transactional
    public List<PedidoProduto> salvarTodos(List<PedidoProduto> list) {
        return pedidoProdutoRepository.saveAll(list);
    }

    public List<PedidoProduto> buscarTodos() {
        return pedidoProdutoRepository.findAll();
    }

    public Optional<PedidoProduto> buscarPorId(UUID id) {
        return pedidoProdutoRepository.findById(id);
    }

    public void deletar(UUID id) {
        pedidoProdutoRepository.deleteById(id);
    }

    public List<PedidoProduto> buscarPorPedido(Pedido pedido) {
        return pedidoProdutoRepository.findAllByPedido(pedido);
    }

    public List<PedidoProduto> buscarPorProduto(Produto produto) {
        return pedidoProdutoRepository.findAllByProduto(produto);
    }
}
