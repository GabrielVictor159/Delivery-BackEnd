package com.Delivery.delivery.dto.genericDTOs;

public class ProdutoRetorno {
    private int Quantidade;

    public int getQuantidade() {
        return this.Quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.Quantidade = quantidade;
    }

    public ProdutoRetorno() {
    }

    public ProdutoRetorno(int Quantidade) {
        this.Quantidade = Quantidade;
    }

    @Override
    public String toString() {
        return "{" +
                " Quantidade='" + getQuantidade() + "'" +
                "}";
    }

}
