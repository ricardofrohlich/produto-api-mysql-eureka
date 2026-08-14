package com.pedidos.produtoapimysql.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedidos.produtoapimysql.model.Produto;
import com.pedidos.produtoapimysql.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class) //qual a classe que estou testando
public class ProdutoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProdutoService service;

    private Produto produto(long id, String nome, double preco, int estoque) {
        Produto p = new Produto();
        p.setId(id);
        p.setNome(nome);
        p.setPreco(preco);
        p.setEstoque(estoque);
        return p;
    }

    @Test
    void salvarProduto() throws Exception {

        Produto salvo = produto(4L, "Coca cola", 9.50, 20);
        given(service.salvar(any(Produto.class))).willReturn(salvo);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(salvo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4));
    }

    @Test
    void listar_deveRetornar200ComAListaDeProdutos() throws Exception {
        given(service.listar()).willReturn(List.of(produto(1L, "Mouse", 50.0, 10)));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Mouse"));
    }

    @Test
    void baixarEstoque() throws Exception {
        Produto atualizado = produto(1L, "Mouse", 50.0, 6);
        given(service.baixarEstoque(1L, 4)).willReturn(atualizado);

        mockMvc.perform(patch("/produtos/1/baixar-estoque").param("quantidade", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estoque").value(6));
    }
}
