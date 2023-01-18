package com.laurasoto.ProyectoAgenda.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepositorio<T> extends JpaRepository<T, Long> {
    List<T> findAll();
}
