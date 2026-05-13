package com.lasertagnation.yachtmodule.service;

import com.lasertagnation.yachtmodule.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodService {

    List<Food> findAll();

    Page<Food> findAll(Pageable pageable);

    Food findById(Long id);

    Food save(Food food);

    void deleteById(Long id);
}
