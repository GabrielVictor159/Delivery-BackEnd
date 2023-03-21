package com.Delivery.delivery.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID id;

    @Column(name = "primeiroNome", nullable = false, length = 30)
    private String primeiroNome;

    @Column(name = "ultimoNome", nullable = false, length = 30)
    private String ultimoNome;

    @Column(nullable = false, length = 80)
    private String cpf;

    @Column(nullable = false, length = 80)
    private String cep;

    @Column(nullable = false)
    private int numeroCasa;

    @Column(nullable = false, length = 80)
    private String telefone;

    @Column(nullable = false)
    private double valor;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    public Pedido() {
        this.dataCriacao = LocalDateTime.now();
    }

    public Pedido(UUID id, String primeiroNome, String ultimoNome, String cpf, String cep, int numeroCasa,
            String telefone, double valor) {
        this.id = id;
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.cpf = cpf;
        this.cep = cep;
        this.numeroCasa = numeroCasa;
        this.telefone = telefone;
        this.valor = valor;
        this.dataCriacao = LocalDateTime.now();

    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPrimeiroNome() {
        return this.primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getUltimoNome() {
        return this.ultimoNome;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getNumeroCasa() {
        return this.numeroCasa;
    }

    public void setNumeroCasa(int numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    @PrePersist
    public void setDataCriacao() {
        this.dataCriacao = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", primeiroNome='" + getPrimeiroNome() + "'" +
                ", ultimoNome='" + getUltimoNome() + "'" +
                ", cpf='" + getCpf() + "'" +
                ", cep='" + getCep() + "'" +
                ", numeroCasa='" + getNumeroCasa() + "'" +
                ", telefone='" + getTelefone() + "'" +
                ", valor='" + getValor() + "'" +
                ", dataCriacao='" + getDataCriacao() + "'" +
                "}";
    }

}
