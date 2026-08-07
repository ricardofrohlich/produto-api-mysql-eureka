package com.pedidos.produtoapimysql.controller;

import com.pedidos.produtoapimysql.model.Categoria;
import com.pedidos.produtoapimysql.service.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Categoria> listar(){
        return service.listar();
    }
    @PostMapping
    public Categoria salvar (@RequestBody Categoria categoria){
        return service.salvar(categoria);
    }
}
