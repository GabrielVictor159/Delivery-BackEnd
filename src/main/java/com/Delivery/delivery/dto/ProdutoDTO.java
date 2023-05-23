package com.Delivery.delivery.dto;

import java.util.Arrays;
import java.util.UUID;

import com.Delivery.delivery.model.Categoria;

public class ProdutoDTO {

    private String nome;
    private String descricao;
    private double preco;
    private byte[][] imagens;
    private Categoria categoria;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String nome, String descricao, double preco, byte[][] imagens, Categoria categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagens = imagens;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public byte[][] getImagens() {
        return imagens;
    }

    public void setImagens(byte[][] imagens) {
        this.imagens = imagens;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "ProdutoDTO [nome=" + nome + ", descricao=" + descricao + ", preco=" + preco + ", imagens="
                + Arrays.toString(imagens) + ", categoria=" + categoria + "]";
    }

}
