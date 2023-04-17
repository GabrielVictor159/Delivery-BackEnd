package com.Delivery.delivery.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Delivery.delivery.model.Admin;
import com.Delivery.delivery.service.AdminService;

@RestController
@RequestMapping(value = "/Admins", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/{nome}/{senha}")
    public ResponseEntity<Object> getAdmin(@PathVariable String nome, @PathVariable String senha) {
        Optional<Admin> admin = adminService.login(nome, senha);
        if (admin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(admin.get(), HttpStatus.OK);
        }
    }

}
