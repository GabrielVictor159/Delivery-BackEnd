package com.Delivery.delivery.dto.genericDTOs;

import java.util.Optional;
import java.util.UUID;

import com.Delivery.delivery.model.Categoria;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class PaginacaoProdutoDTO {
    private Optional<String> nome = Optional.empty();
    private Optional<String> categoria = Optional.empty();
    @Min(value = -1, message = "O preço minimo deve ser um valor valido")
    private double precoMinimo = -1;
    @Min(value = -1, message = "O preço maximo deve ser um valor valido")
    private double precoMaximo = -1;
    private Optional<String> descricao = Optional.empty();
    @Min(value = 1, message = "O tamanho Minimo da página é 1")
    private int pagina = 1;
    @Max(value = 100, message = "O tamanho Maximo de itens da página é 100")
    @Min(value = 0)
    private int tamanhoPagina = 10;

    public PaginacaoProdutoDTO() {
    }

    public Optional<String> getNome() {
        return nome;
    }

    public void setNome(Optional<String> nome) {
        this.nome = nome;
    }

    public Optional<String> getCategoria() {
        return categoria;
    }

    public void setCategoria(Optional<String> categoria) {
        this.categoria = categoria;
    }

    public double getPrecoMinimo() {
        return precoMinimo;
    }

    public void setPrecoMinimo(double precoMinimo) {
        this.precoMinimo = precoMinimo;
    }

    public double getPrecoMaximo() {
        return precoMaximo;
    }

    public void setPrecoMaximo(double precoMaximo) {
        this.precoMaximo = precoMaximo;
    }

    public Optional<String> getDescricao() {
        return descricao;
    }

    public void setDescricao(Optional<String> descricao) {
        this.descricao = descricao;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getTamanhoPagina() {
        return tamanhoPagina;
    }

    public void setTamanhoPagina(int tamanhoPagina) {
        this.tamanhoPagina = tamanhoPagina;
    }

}
