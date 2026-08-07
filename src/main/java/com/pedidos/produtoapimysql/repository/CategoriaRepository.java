package com.pedidos.produtoapimysql.repository;

import com.pedidos.produtoapimysql.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
