package com.Delivery.delivery.dto;

import java.util.Arrays;

public class CategoriaDTO {

    private String nome;
    private byte[] imagem;

    public CategoriaDTO() {
    }

    public CategoriaDTO(String nome, byte[] imagem) {
        this.nome = nome;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return "CategoriaDTO [nome=" + nome + ", imagem=" + Arrays.toString(imagem) + "]";
    }

}
