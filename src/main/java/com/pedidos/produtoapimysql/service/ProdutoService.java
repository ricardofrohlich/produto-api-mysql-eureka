package com.pedidos.produtoapimysql.service;


import com.pedidos.produtoapimysql.model.Produto;
import com.pedidos.produtoapimysql.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto salvar(Produto produto){
        return repository.save(produto);
    }

    public Produto buscarPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
    public void deletar (Long id){
        repository.deleteById(id);
    }
    public List<Produto> listar(){
        return repository.findAll();
    }

    public Produto baixarEstoque(Long id, Integer quantidade){
        Produto produto = buscarPorId(id);
        if(produto.getEstoque() < quantidade){
            throw new IllegalArgumentException("Estoque insuficiente para o produdo "+id);
        }
        produto.setEstoque(produto.getEstoque() - quantidade);
        return repository.save(produto);
    }
}
