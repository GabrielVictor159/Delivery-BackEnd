package com.Delivery.delivery.dto;

import java.util.UUID;

import com.Delivery.delivery.model.Categoria;

public class ProdutoDTO {

    private String nome;
    private String descricao;
    private double preco;
    private String imagens;
    private Categoria categoria;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String nome, String descricao, double preco, String imagens, Categoria categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagens = imagens;
        this.categoria = categoria;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return this.preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getImagens() {
        return this.imagens;
    }

    public void setImagens(String imagens) {
        this.imagens = imagens;
    }

    public Categoria getcategoria() {
        return this.categoria;
    }

    public void setcategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "{" +
                ", nome='" + getNome() + "'" +
                ", descricao='" + getDescricao() + "'" +
                ", preco='" + getPreco() + "'" +
                ", imagens='" + getImagens() + "'" +
                ", categoria='" + getcategoria() + "'" +
                "}";
    }
}
