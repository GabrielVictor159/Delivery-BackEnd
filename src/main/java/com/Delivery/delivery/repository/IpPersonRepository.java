package com.Delivery.delivery.repository;

import org.springframework.stereotype.Repository;

import com.Delivery.delivery.model.ipPerson;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IpPersonRepository extends JpaRepository<ipPerson, UUID> {

}
