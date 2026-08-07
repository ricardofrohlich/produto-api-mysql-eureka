package com.pedidos.produtoapimysql.controller;


import com.pedidos.produtoapimysql.model.Produto;
import com.pedidos.produtoapimysql.service.ProdutoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public Produto salvar(@RequestBody Produto produto){
        return service.salvar(produto);
    }

    @GetMapping
    public List<Produto> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }

    @PatchMapping("/{id}/baixar-estoque")
    public Produto baixarEstoque(@PathVariable Long id, @RequestParam Integer quantidade){
        return service.baixarEstoque(id, quantidade);
    }
}
