package com.Delivery.delivery.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Delivery.delivery.model.Pedido;
import com.Delivery.delivery.repository.PedidoProdutoRepository;
import com.Delivery.delivery.repository.PedidosRepository;

@Service
public class PedidosService {
    @Autowired
    PedidosRepository pedidosRepository;
    @Autowired
    PedidoProdutoRepository pedidoProdutoRepository;

    public Pedido salvar(Pedido pedido) {
        return pedidosRepository.save(pedido);
    }

    public List<Pedido> buscarTodos() {
        return pedidosRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(UUID id) {
        return pedidosRepository.findById(id);
    }

    public void deletar(UUID id) {
        Optional<Pedido> pedido = pedidosRepository.findById(id);
        if (pedido.isPresent()) {
            try {
                pedidoProdutoRepository.deleteAllByPedido(pedido.get());
                pedidosRepository.deleteById(id);
            } catch (Exception e) {

            }
        }

    }
}
