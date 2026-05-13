package com.lasertagnation.yachtmodule.controller;

import com.lasertagnation.yachtmodule.entity.Food;
import com.lasertagnation.yachtmodule.service.FoodService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/yacht-module/foods")
public class YachtModuleFoodController {

    private final FoodService foodService;

    public YachtModuleFoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public List<Food> getAll() {
        return foodService.findAll();
    }

    @GetMapping("/paged")
    public Page<Food> getPaged(Pageable pageable) {
        return foodService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getById(@PathVariable Long id) {
        Food f = foodService.findById(id);
        return f == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(f);
    }

    @PostMapping
    public Food create(@RequestBody Food food) {
        return foodService.save(food);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> update(@PathVariable Long id, @RequestBody Food food) {
        if (foodService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        food.setId(id);
        return ResponseEntity.ok(foodService.save(food));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        foodService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
