package com.TD5.spring.service;

import com.TD5.spring.entity.Dish;
import com.TD5.spring.entity.Ingredient;
import com.TD5.spring.repository.DishRepository;
import com.TD5.spring.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;

    public DishService(DishRepository dishRepository,
                       IngredientRepository ingredientRepository) {
        this.dishRepository = dishRepository;
        this.ingredientRepository = ingredientRepository;
    }

    // GET /dishes avec filtres optionnels
    public List<Dish> getAll(Double priceUnder, Double priceOver, String name) {
        if (priceUnder == null && priceOver == null && name == null) {
            return dishRepository.findAll();
        }
        return dishRepository.findWithFilters(priceUnder, priceOver, name);
    }

    // POST /dishes
    public List<Dish> createDishes(List<Dish> newDishes) {
        List<Dish> created = new ArrayList<>();
        for (Dish dish : newDishes) {
            if (dishRepository.existsByName(dish.getName())) {
                throw new RuntimeException(
                        "Dish.name=" + dish.getName() + " already exists");
            }
            created.add(dishRepository.save(dish));
        }
        return created;
    }

    // PUT /dishes/{id}/ingredients
    public Dish updateIngredients(Long dishId, List<Ingredient> newIngredients) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new RuntimeException(
                        "Dish.id=" + dishId + " is not found"));

        if (newIngredients == null || newIngredients.isEmpty()) {
            throw new RuntimeException("Request body is required");
        }

        // Garder seulement les ingrédients qui existent en mémoire
        List<Ingredient> validIngredients = newIngredients.stream()
                .filter(i -> i.getId() != null
                        && ingredientRepository.existsById(i.getId()))
                .map(i -> ingredientRepository.findById(i.getId()).get())
                .collect(Collectors.toList());

        dish.setIngredients(validIngredients);
        return dishRepository.update(dish);
    }

}