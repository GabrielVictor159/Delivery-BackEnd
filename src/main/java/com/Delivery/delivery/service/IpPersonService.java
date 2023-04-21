package com.Delivery.delivery.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Delivery.delivery.model.IpPerson;
import com.Delivery.delivery.repository.IpPersonRepository;

@Service
public class IpPersonService {
    @Autowired
    IpPersonRepository ipPersonRepository;

    public IpPerson save(IpPerson ipPerson) {
        return ipPersonRepository.save(ipPerson);
    }

    public Optional<IpPerson> findOne(UUID id) {
        return ipPersonRepository.findById(id);
    }

    public void deletar(UUID id) {
        ipPersonRepository.deleteById(id);
    }
}
