package com.TD5.spring.repository;

import com.TD5.spring.entity.Dish;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class DishRepository {

    private final List<Dish> dishes = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Dish> findAll() {
        return dishes;
    }

    public Optional<Dish> findById(Long id) {
        return dishes.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    public boolean existsByName(String name) {
        return dishes.stream()
                .anyMatch(d -> d.getName().equals(name));
    }

    public Dish save(Dish dish) {
        dish.setId(counter.getAndIncrement());
        dishes.add(dish);
        return dish;
    }

    public Dish update(Dish dish) {
        // Remplace le plat existant
        for (int i = 0; i < dishes.size(); i++) {
            if (dishes.get(i).getId().equals(dish.getId())) {
                dishes.set(i, dish);
                return dish;
            }
        }
        return dish;
    }

    // Filtrer les plats
    public List<Dish> findWithFilters(Double priceUnder, Double priceOver, String name) {
        return dishes.stream()
                .filter(d -> priceUnder == null || d.getPrice() < priceUnder)
                .filter(d -> priceOver == null || d.getPrice() > priceOver)
                .filter(d -> name == null || d.getName().toLowerCase()
                        .contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}