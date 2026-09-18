package com.pedidos.produtoapimysql.repository;
import com.pedidos.produtoapimysql.model.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository repository;

    @Test
    void save_devePersistirEGerarId() {

        Produto produto = new Produto();

        produto.setNome("Teclado");
        produto.setPreco(150.0);
        produto.setEstoque(20);

        Produto salvo = repository.save(produto);

        assertThat(salvo.getId())
                .isNotZero();

        Optional<Produto> encontrado =
                repository.findById(salvo.getId());

        assertThat(encontrado)
                .isPresent();

        assertThat(encontrado.get().getNome())
                .isEqualTo("Teclado");
    }
    @Test
    void findAll_deveListarProdutosPersistidos() {
        Produto p1 = new Produto();
        p1.setNome("Monitor");
        p1.setPreco(900.0);
        p1.setEstoque(5);

        Produto p2 = new Produto();
        p2.setNome("Webcam");
        p2.setPreco(200.0);
        p2.setEstoque(15);

        repository.save(p1);
        repository.save(p2);

        List<Produto> produtos = repository.findAll();

        assertThat(produtos).hasSize(2);
    }

    @Test
    void deleteById_deveRemoverProduto() {
        Produto produto = new Produto();
        produto.setNome("Mousepad");
        produto.setPreco(30.0);
        produto.setEstoque(50);
        Produto salvo = repository.save(produto);

        repository.deleteById(salvo.getId());

        assertThat(repository.findById(salvo.getId())).isEmpty();
    }
}