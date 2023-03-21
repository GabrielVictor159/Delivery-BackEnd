package com.Delivery.delivery.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Delivery.delivery.model.Pedido;

@Repository
public interface PedidosRepository extends JpaRepository<Pedido, UUID> {
}
