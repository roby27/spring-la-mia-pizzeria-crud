package it.r27.pizzeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.r27.pizzeria.model.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    public List<Pizza> findByNameContaining(String name);
}
