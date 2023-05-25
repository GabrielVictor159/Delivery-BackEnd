package com.Delivery.delivery.model;

import java.io.Serializable;
import java.util.UUID;

import com.Delivery.delivery.functions.MD5Encoder;
import com.Delivery.delivery.model.Enums.typeAdmins;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin implements Serializable {
    private static final long serialVersionUID = 1l;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID id;

    @Column(nullable = false, length = 140)
    private String nome;

    @Column(nullable = false, length = 140)
    private String senha;

    @Column(nullable = false, length = 140)
    @Enumerated(EnumType.STRING)
    private typeAdmins tipo;

    public Admin() {
    }

    public Admin(UUID id, String nome, String senha, typeAdmins tipo) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.tipo = tipo;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public typeAdmins getTipo() {
        return this.tipo;
    }

    public void setTipo(typeAdmins tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", nome='" + getNome() + "'" +
                ", senha='" + getSenha() + "'" +
                ", tipo='" + getTipo() + "'" +
                "}";
    }

}
