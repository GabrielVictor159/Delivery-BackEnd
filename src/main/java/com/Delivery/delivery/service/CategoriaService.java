package com.Delivery.delivery.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Delivery.delivery.model.Categoria;
import com.Delivery.delivery.model.PedidoProduto;
import com.Delivery.delivery.model.Produto;
import com.Delivery.delivery.repository.CategoriaRepository;
import com.Delivery.delivery.repository.PedidoProdutoRepository;
import com.Delivery.delivery.repository.PedidosRepository;
import com.Delivery.delivery.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    PedidoProdutoRepository pedidoProdutoRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    PedidosRepository pedidosRepository;

    @Transactional
    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> buscarTodos() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarPorId(UUID id) {
        return categoriaRepository.findById(id);
    }

    public Optional<Categoria> buscarPorNome(String nome) {
        return categoriaRepository.findByNome(nome);
    }

    @Transactional
    public void deletar(UUID id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            try {
                List<Produto> produtos = produtoRepository.findAllByCategoria(categoria.get());
                for (Produto p : produtos) {
                    try {
                        List<PedidoProduto> pedidoProdutos = pedidoProdutoRepository.findAllByProduto(p);
                        pedidoProdutoRepository.deleteAllByProduto(p);
                        for (PedidoProduto g : pedidoProdutos) {
                            pedidosRepository.deleteById(g.getId());
                        }
                    } catch (Exception z) {

                    }

                }
                categoriaRepository.deleteById(id);
            } catch (Exception e) {
            }
        }

    }
}
