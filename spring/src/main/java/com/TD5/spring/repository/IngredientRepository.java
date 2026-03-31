package com.TD5.spring.repository;

import com.TD5.spring.entity.Ingredient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class IngredientRepository {

    private final List<Ingredient> ingredients = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Ingredient> findAll() {
        return ingredients;
    }

    public Optional<Ingredient> findById(Long id) {
        return ingredients.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    public boolean existsById(Long id) {
        return ingredients.stream().anyMatch(i -> i.getId().equals(id));
    }

    public Ingredient save(Ingredient ingredient) {
        ingredient.setId(counter.getAndIncrement());
        ingredients.add(ingredient);
        return ingredient;
    }
}