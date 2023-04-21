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

import com.Delivery.delivery.model.Pedido;
import com.Delivery.delivery.repository.PedidoProdutoRepository;
import com.Delivery.delivery.repository.PedidosRepository;

@Service
public class PedidosService {
    @Autowired
    PedidosRepository pedidosRepository;
    @Autowired
    PedidoProdutoRepository pedidoProdutoRepository;
    @Autowired
    IpPersonService ipPersonService;

    public Pedido salvar(Pedido pedido) {
        return pedidosRepository.save(pedido);
    }

    public List<Pedido> buscarTodos() {
        return pedidosRepository.findAll();
    }

    public Page<Pedido> buscarPedidosPaginados(int pagina, int tamanhoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        return pedidosRepository.findAll(pageable);
    }

    public Optional<Pedido> buscarPorId(UUID id) {
        return pedidosRepository.findById(id);
    }

    public void deletar(UUID id) {
        Optional<Pedido> pedido = pedidosRepository.findById(id);

        if (pedido.isPresent()) {
            UUID idIp = pedido.get().getIpPerson().getId();
            try {
                pedidoProdutoRepository.deleteAllByPedido(pedido.get());
                pedidosRepository.deleteById(id);
                ipPersonService.deletar(idIp);
            } catch (Exception e) {

            }
        }

    }
}
