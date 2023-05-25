package com.Delivery.delivery.dto;

import java.util.Arrays;
import java.util.UUID;

public class ProdutoDTO {

    private String nome;
    private String descricao;
    private double preco;
    private byte[][] imagens;
    private UUID idCategoria;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String nome, String descricao, double preco, byte[][] imagens, UUID idCategoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagens = imagens;
        this.idCategoria = idCategoria;
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

    public UUID getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(UUID idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "ProdutoDTO [nome=" + nome + ", descricao=" + descricao + ", preco=" + preco + ", imagens="
                + Arrays.toString(imagens) + ", idCategoria=" + idCategoria + "]";
    }

}
