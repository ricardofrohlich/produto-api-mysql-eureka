package com.pedidos.produtoapimysql.service;

import com.pedidos.produtoapimysql.model.Produto;
import com.pedidos.produtoapimysql.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    private Produto produtoComEstoque(int estoque) {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Mouse");
        produto.setPreco(50.0);
        produto.setEstoque(estoque);
        return produto;
    }

    @Test
    void salvar_deveDelegarParaRepository() {
        Produto produto = produtoComEstoque(10);
        when(repository.save(produto)).thenReturn(produto);

        Produto salvo = service.salvar(produto);

        assertThat(salvo).isEqualTo(produto);
        verify(repository).save(produto);
    }

    @Test
    void buscarPorId_deveRetornarProduto_quandoEncontrado() {
        Produto produto = produtoComEstoque(10);
        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        Produto encontrado = service.buscarPorId(1L);

        assertThat(encontrado).isEqualTo(produto);
    }

    @Test
    void buscarPorId_deveLancarExcecao_quandoNaoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void listar_deveRetornarTodosOsProdutos() {
        List<Produto> produtos = List.of(produtoComEstoque(10), produtoComEstoque(5));
        when(repository.findAll()).thenReturn(produtos);

        assertThat(service.listar()).hasSize(2);
    }

    @Test
    void deletar_deveChamarRepositoryDeleteById() {
        service.deletar(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void baixarEstoque_deveReduzirEstoque_quandoSuficiente() {
        Produto produto = produtoComEstoque(10);
        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenAnswer(inv -> inv.getArgument(0));

        Produto atualizado = service.baixarEstoque(1L, 4);

        assertThat(atualizado.getEstoque()).isEqualTo(6);
        verify(repository).save(produto);
    }

    @Test
    void baixarEstoque_deveLancarExcecao_quandoEstoqueInsuficiente() {
        Produto produto = produtoComEstoque(3);
        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        assertThrows(IllegalArgumentException.class, () -> service.baixarEstoque(1L, 5));
        verify(repository, never()).save(any());
    }
}
