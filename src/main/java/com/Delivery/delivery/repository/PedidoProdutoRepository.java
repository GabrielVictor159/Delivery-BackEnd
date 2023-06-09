package com.Delivery.delivery.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Delivery.delivery.model.Pedido;
import com.Delivery.delivery.model.PedidoProduto;
import com.Delivery.delivery.model.Produto;

@Repository
public interface PedidoProdutoRepository extends JpaRepository<PedidoProduto, UUID> {
    List<PedidoProduto> findAllByPedido(Pedido pedido);

    List<PedidoProduto> findAllByProduto(Produto produto);

    void deleteAllByProduto(Produto produto);

    void deleteAllByPedido(Pedido pedido);
}
