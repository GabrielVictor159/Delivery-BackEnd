package com.Delivery.delivery.dto;

import javax.validation.constraints.NotBlank;

public class PedidoDTO {
    @NotBlank
    private String primeiroNome;
    @NotBlank
    private String ultimoNome;
    @NotBlank
    private String cpf;
    @NotBlank
    private int cep;
    @NotBlank
    private int numeroCasa;
    @NotBlank
    private String telefone;

    public PedidoDTO() {
    }

    public PedidoDTO(String primeiroNome, String ultimoNome, String cpf, int cep, int numeroCasa,
            String telefone) {
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.cpf = cpf;
        this.cep = cep;
        this.numeroCasa = numeroCasa;
        this.telefone = telefone;
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

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
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

}
