package com.Delivery.delivery.dto.genericDTOs;

import java.util.List;

public class PedidoRetorno {
    private List<ProdutoRetorno> Produtos;

    public List<ProdutoRetorno> getProdutos() {
        return Produtos;
    }

    public void setProdutos(List<ProdutoRetorno> Produtos) {
        this.Produtos = Produtos;
    }

    public PedidoRetorno() {
    }

    public PedidoRetorno(List<ProdutoRetorno> Produtos) {
        this.Produtos = Produtos;
    }

    @Override
    public String toString() {
        return "{" +
                " Produtos='" + getProdutos() + "'" +
                "}";
    }

}
