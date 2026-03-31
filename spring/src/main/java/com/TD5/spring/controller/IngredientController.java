package com.TD5.spring.controller;

import com.TD5.spring.entity.Ingredient;
import com.TD5.spring.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IngredientController {

    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    // GET /ingredients
    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredient>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // GET /ingredients/{id}
    @GetMapping("/ingredients/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // GET /ingredients/{id}/stock?at=&unit=
    @GetMapping("/ingredients/{id}/stock")
    public ResponseEntity<?> getStock(
            @PathVariable Long id,
            @RequestParam(required = false) String at,
            @RequestParam(required = false) String unit) {

        if (at == null || unit == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Either mandatory query parameter `at` or `unit` is not provided.");
        }
        try {
            service.getById(id);
            return ResponseEntity.ok(
                    "{ \"unit\": \"" + unit + "\", \"value\": 100 }");
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}