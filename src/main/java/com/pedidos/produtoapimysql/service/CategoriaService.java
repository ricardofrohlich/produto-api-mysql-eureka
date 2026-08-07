package com.pedidos.produtoapimysql.service;

import com.pedidos.produtoapimysql.model.Categoria;
import com.pedidos.produtoapimysql.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> listar(){
        return repository.findAll();
    }
    public Categoria salvar(Categoria categoria){
        return repository.save(categoria);
    }

}
