package com.Delivery.delivery.dto.genericDTOs;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PedidosRetornoFuncionario {
    private UUID id;
    private String primeiroNome;
    private String ultimoNome;
    private String cep;
    private Integer numeroCasa;
    private String telefone;
    private Date dataCriacao;
    private Double ValorTotal;
    private String ip;
    private String metodoPagamento;
    private List<ProdutoRetorno> Produtos;

    public PedidosRetornoFuncionario() {
    }

    public PedidosRetornoFuncionario(UUID id, String primeiroNome, String ultimoNome, String cep, Integer numeroCasa,
            String telefone, Date dataCriacao, Double valorTotal, String ip, String metodoPagamento,
            List<ProdutoRetorno> produtos) {
        this.id = id;
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.cep = cep;
        this.numeroCasa = numeroCasa;
        this.telefone = telefone;
        this.dataCriacao = dataCriacao;
        ValorTotal = valorTotal;
        this.ip = ip;
        this.metodoPagamento = metodoPagamento;
        Produtos = produtos;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(Integer numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Double getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        ValorTotal = valorTotal;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public List<ProdutoRetorno> getProdutos() {
        return Produtos;
    }

    public void setProdutos(List<ProdutoRetorno> produtos) {
        Produtos = produtos;
    }

    @Override
    public String toString() {
        return "PedidosRetornoFuncionario [id=" + id + ", primeiroNome=" + primeiroNome + ", ultimoNome=" + ultimoNome
                + ", cep=" + cep + ", numeroCasa=" + numeroCasa + ", telefone=" + telefone + ", dataCriacao="
                + dataCriacao + ", ValorTotal=" + ValorTotal + ", ip=" + ip + ", metodoPagamento=" + metodoPagamento
                + ", Produtos=" + Produtos + "]";
    }

}
