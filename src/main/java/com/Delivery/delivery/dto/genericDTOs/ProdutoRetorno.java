package com.Delivery.delivery.dto.genericDTOs;

import java.util.UUID;

import com.Delivery.delivery.model.Produto;

public class ProdutoRetorno extends Produto {
    private int Quantidade;

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ProdutoRetorno [Quantidade=" + Quantidade + "]";
    }

    public ProdutoRetorno() {

    }

}
