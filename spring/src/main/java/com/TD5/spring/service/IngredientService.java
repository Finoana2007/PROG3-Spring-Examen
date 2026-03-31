package com.TD5.spring.service;

import com.TD5.spring.entity.Ingredient;
import com.TD5.spring.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    public List<Ingredient> getAll() {
        return repository.findAll();
    }

    public Ingredient getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Ingredient.id=" + id + " is not found"));
    }

    public Ingredient save(Ingredient ingredient) {
        return repository.save(ingredient);
    }
}