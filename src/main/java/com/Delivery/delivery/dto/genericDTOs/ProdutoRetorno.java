package com.Delivery.delivery.dto.genericDTOs;

import java.util.UUID;

public class ProdutoRetorno {
    private UUID id;
    private int Quantidade;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ProdutoRetorno [id=" + id + ", Quantidade=" + Quantidade + "]";
    }

}
