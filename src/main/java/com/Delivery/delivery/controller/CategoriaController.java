package com.Delivery.delivery.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Delivery.delivery.dto.CategoriaDTO;
import com.Delivery.delivery.model.Admin;
import com.Delivery.delivery.model.Categoria;
import com.Delivery.delivery.service.AdminService;
import com.Delivery.delivery.service.CategoriaService;
import com.Delivery.delivery.service.ImageService;

@RestController
@RequestMapping(value = "/Categorias", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin
public class CategoriaController {

    @Autowired
    AdminService adminService;
    @Autowired
    CategoriaService categoriaService;
    @Autowired
    ImageService imageService;

    @GetMapping()
    public ResponseEntity<Object> getAll() {
        List<Categoria> listCategorias = categoriaService.buscarTodos();
        return new ResponseEntity<>(listCategorias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable UUID id) {
        Optional<Categoria> categoria = categoriaService.buscarPorId(id);
        if (categoria.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(categoria.get(), HttpStatus.OK);
        }

    }

    @PostMapping("/{nomeAdmin}/{senhaAdmin}")
    public ResponseEntity<Object> adicionar(@PathVariable String nomeAdmin, @PathVariable String senhaAdmin,
            @RequestBody CategoriaDTO dto) {
        Optional<Admin> admin = adminService.login(nomeAdmin, senhaAdmin);
        if (admin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            try {
                Categoria categoria = new Categoria();
                String image = imageService.salvar(dto.getImagem());
                BeanUtils.copyProperties(dto, categoria);
                categoria.setImagem(image);
                categoriaService.salvar(categoria);
                return new ResponseEntity<>(categoria, HttpStatus.OK);
            } catch (Exception e) {
                System.out.println(e.getMessage());

                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PutMapping("/{id}/{nomeAdmin}/{senhaAdmin}")
    public ResponseEntity<Object> atualizar(@PathVariable UUID id, @PathVariable String nomeAdmin,
            @PathVariable String senhaAdmin, @RequestBody CategoriaDTO dto) {
        Optional<Admin> admin = adminService.login(nomeAdmin, senhaAdmin);
        if (admin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            Optional<Categoria> categoria = categoriaService.buscarPorId(id);
            if (categoria.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                try {
                    imageService.excluir(categoria.get().getImagem());
                    BeanUtils.copyProperties(dto, categoria.get());
                    String image = imageService.salvar(dto.getImagem());
                    categoria.get().setImagem(image);
                    categoriaService.salvar(categoria.get());
                    return new ResponseEntity<>(categoria.get(), HttpStatus.OK);
                } catch (Exception e) {
                    System.out.println(e.getMessage());

                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
    }

    @DeleteMapping("/{id}/{nomeAdmin}/{senhaAdmin}")
    public ResponseEntity<Object> deletar(@PathVariable UUID id, @PathVariable String nomeAdmin,
            @PathVariable String senhaAdmin) {
        Optional<Admin> admin = adminService.login(nomeAdmin, senhaAdmin);
        if (admin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            Optional<Categoria> categoria = categoriaService.buscarPorId(id);
            if (categoria.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                try {
                    imageService.excluir(categoria.get().getImagem());
                    categoriaService.deletar(categoria.get().getId());
                    return new ResponseEntity<>(HttpStatus.OK);
                } catch (Exception e) {
                    System.out.println(e.getMessage());

                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

        }
    }

}
