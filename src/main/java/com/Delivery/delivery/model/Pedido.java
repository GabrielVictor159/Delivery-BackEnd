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
public class Pedido extends RepresentationModel<Pedido> implements Serializable {
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
    private Integer cpf;

    @Column(name = "cep", length = 80, nullable = false)
    private String cep;

    @Column(name = "numeroCasa", nullable = false)
    private Integer numeroCasa;

    @Column(name = "telefone", length = 80, nullable = false)
    private String telefone;

    @Column(name = "dataCriacao", nullable = false)
    private Date dataCriacao;

    @ManyToOne
    @JoinColumn(name = "ipId", referencedColumnName = "id")
    private IpPerson ipPerson;

    public Pedido() {
        this.dataCriacao = new Date();
    }

    public Pedido(String primeiroNome, String ultimoNome, Integer cpf, String cep, Integer numeroCasa,
            String telefone, IpPerson ipPerson) {
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.cpf = cpf;
        this.cep = cep;
        this.numeroCasa = numeroCasa;
        this.telefone = telefone;
        this.dataCriacao = new Date();
        this.ipPerson = ipPerson;
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

    public Integer getCpf() {
        return this.cpf;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNumeroCasa() {
        return this.numeroCasa;
    }

    public void setNumeroCasa(Integer numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataCriacao() {
        return this.dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public IpPerson getIpPerson() {
        return this.ipPerson;
    }

    public void setIpPerson(IpPerson ipPerson) {
        this.ipPerson = ipPerson;
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
                ", dataCriacao='" + getDataCriacao() + "'" +
                ", ipPerson='" + getIpPerson() + "'" +
                "}";
    }

}
