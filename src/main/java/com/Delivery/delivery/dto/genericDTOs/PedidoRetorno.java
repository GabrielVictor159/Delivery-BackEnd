package com.Delivery.delivery.dto.genericDTOs;

import java.util.List;
import java.util.UUID;

public class PedidoRetorno {
    private UUID id;
    private Double ValorTotal;
    private String metodoPagamento;
    private List<ProdutoRetorno> Produtos;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        ValorTotal = valorTotal;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public List<ProdutoRetorno> getProdutos() {
        return Produtos;
    }

    public void setProdutos(List<ProdutoRetorno> produtos) {
        Produtos = produtos;
    }

    public PedidoRetorno() {
    }

    public PedidoRetorno(UUID id, Double valorTotal, String metodoPagamento, List<ProdutoRetorno> produtos) {
        this.id = id;
        ValorTotal = valorTotal;
        this.metodoPagamento = metodoPagamento;
        Produtos = produtos;
    }

    @Override
    public String toString() {
        return "PedidoRetorno [id=" + id + ", ValorTotal=" + ValorTotal + ", metodoPagamento=" + metodoPagamento
                + ", Produtos=" + Produtos + "]";
    }

}
