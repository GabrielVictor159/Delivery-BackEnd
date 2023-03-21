package com.Delivery.delivery.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Delivery.delivery.functions.MD5Encoder;
import com.Delivery.delivery.model.Admin;
import com.Delivery.delivery.repository.AdminRepository;

@Service
public class AdminService {
   @Autowired
   AdminRepository adminRepository;

   public Optional<Admin> login(String nome, String senha) {
      return adminRepository.findByNomeAndSenha(nome, MD5Encoder.encode(senha));
   }

}
