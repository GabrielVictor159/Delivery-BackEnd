package com.Delivery.delivery.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1l;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID id;

    @Column(name = "primeiroNome", length = 30, nullable = false)
    private String primeiroNome;

    @Column(name = "ultimoNome", length = 30, nullable = false)
    private String ultimoNome;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "cep", length = 80, nullable = false)
    private String cep;

    @Column(name = "numeroCasa", nullable = false)
    private Integer numeroCasa;

    @Column(name = "telefone", length = 80, nullable = false)
    private String telefone;

    @Column(name = "dataCriacao", nullable = false)
    private Date dataCriacao;

    @Column(nullable = false)
    private Double ValorTotal;

    @ManyToOne
    @JoinColumn(name = "ipId", referencedColumnName = "id")
    private IpPerson ipPerson;

    @Column(nullable = false)
    private String metodoPagamento;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    public Pedido() {
        this.dataCriacao = new Date();
    }

    public Pedido(UUID id, String primeiroNome, String ultimoNome, String cpf, String cep, Integer numeroCasa,
            String telefone, Double valorTotal, IpPerson ipPerson, String metodoPagamento,
            String observacoes) {
        this.id = id;
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.cpf = cpf;
        this.cep = cep;
        this.numeroCasa = numeroCasa;
        this.telefone = telefone;
        this.dataCriacao = new Date();
        ValorTotal = valorTotal;
        this.ipPerson = ipPerson;
        this.metodoPagamento = metodoPagamento;
        this.observacoes = observacoes;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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

    public IpPerson getIpPerson() {
        return ipPerson;
    }

    public void setIpPerson(IpPerson ipPerson) {
        this.ipPerson = ipPerson;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public String toString() {
        return "Pedido [id=" + id + ", primeiroNome=" + primeiroNome + ", ultimoNome=" + ultimoNome + ", cpf=" + cpf
                + ", cep=" + cep + ", numeroCasa=" + numeroCasa + ", telefone=" + telefone + ", dataCriacao="
                + dataCriacao + ", ValorTotal=" + ValorTotal + ", ipPerson=" + ipPerson + ", metodoPagamento="
                + metodoPagamento + ", observacoes=" + observacoes + "]";
    }

}
