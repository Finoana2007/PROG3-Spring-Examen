package com.TD5.spring.controller;

import com.TD5.spring.entity.Dish;
import com.TD5.spring.entity.Ingredient;
import com.TD5.spring.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DishController {

    private final DishService service;

    public DishController(DishService service) {
        this.service = service;
    }

    // GET /dishes?priceUnder=&priceOver=&name=
    @GetMapping("/dishes")
    public ResponseEntity<List<Dish>> getAll(
            @RequestParam(required = false) Double priceUnder,
            @RequestParam(required = false) Double priceOver,
            @RequestParam(required = false) String name) {
        return ResponseEntity.ok(service.getAll(priceUnder, priceOver, name));
    }

    // POST /dishes
    @PostMapping("/dishes")
    public ResponseEntity<?> createDishes(@RequestBody List<Dish> dishes) {
        try {
            List<Dish> created = service.createDishes(dishes);
            return ResponseEntity
                    .status(HttpStatus.CREATED) // 201
                    .body(created);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("already exists")) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST) // 400
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                    .body(e.getMessage());
        }
    }

    // PUT /dishes/{id}/ingredients
    @PutMapping("/dishes/{id}/ingredients")
    public ResponseEntity<?> updateIngredients(
            @PathVariable Long id,
            @RequestBody(required = false) List<Ingredient> ingredients) {

        if (ingredients == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Request body is required");
        }
        try {
            Dish updated = service.updateIngredients(id, ingredients);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("is not found")) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
