package com.Delivery.delivery.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Delivery.delivery.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    Optional<Categoria> findByNome(String nome);
}
