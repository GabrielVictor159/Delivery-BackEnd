package com.Delivery.delivery.dto;

public class PedidoDTO {

    private String primeiroNome;
    private String ultimoNome;
    private String cpf;
    private String cep;
    private int numeroCasa;
    private String telefone;

    public PedidoDTO() {
    }

    public PedidoDTO(String primeiroNome, String ultimoNome, String cpf, String cep, int numeroCasa,
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

}
