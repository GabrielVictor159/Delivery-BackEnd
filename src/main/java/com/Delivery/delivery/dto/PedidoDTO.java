package com.Delivery.delivery.dto;

import java.util.List;

import com.Delivery.delivery.dto.genericDTOs.listProducts;
import com.Delivery.delivery.model.Enums.PaymentMethods;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PedidoDTO {
    @NotBlank
    private String primeiroNome;
    @NotBlank
    private String ultimoNome;
    @NotBlank
    private String cpf;
    @NotNull
    private String cep;
    @NotNull
    private int numeroCasa;
    @NotBlank
    private String telefone;
    @NotNull
    private PaymentMethods metodoPagamento;

    private String observacoes;

    private List<listProducts> produtos;

    public PedidoDTO() {
    }

    public PedidoDTO(@NotBlank String primeiroNome, @NotBlank String ultimoNome, @NotBlank String cpf,
            @NotNull String cep, @NotNull int numeroCasa, @NotBlank String telefone,
            @NotNull PaymentMethods metodoPagamento, String observacoes, List<listProducts> produtos) {
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.cpf = cpf;
        this.cep = cep;
        this.numeroCasa = numeroCasa;
        this.telefone = telefone;
        this.metodoPagamento = metodoPagamento;
        this.observacoes = observacoes;
        this.produtos = produtos;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(int numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public PaymentMethods getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(PaymentMethods metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public List<listProducts> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<listProducts> produtos) {
        this.produtos = produtos;
    }

}
