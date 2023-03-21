package com.Delivery.delivery.dto;

public class CategoriaDTO {

    private String nome;

    public CategoriaDTO() {
    }

    public CategoriaDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "{" +
                ", nome='" + getNome() + "'" +
                "}";
    }
}
