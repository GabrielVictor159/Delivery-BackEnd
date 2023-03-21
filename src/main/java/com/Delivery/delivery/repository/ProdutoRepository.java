package com.Delivery.delivery.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Delivery.delivery.model.Categoria;
import com.Delivery.delivery.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    List<Produto> findByCategoria(Categoria categoria);

    void deleteAllByCategoria(Categoria categoria);
}
