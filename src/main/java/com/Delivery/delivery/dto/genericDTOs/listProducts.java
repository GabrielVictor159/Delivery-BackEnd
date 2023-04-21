package com.Delivery.delivery.dto.genericDTOs;

import java.util.UUID;

public class listProducts {
    private UUID id;
    private int quantidade;

    public listProducts() {
    }

    public listProducts(UUID id, int quantidade) {
        this.id = id;
        this.quantidade = quantidade;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", quantidade='" + getQuantidade() + "'" +
                "}";
    }

}
