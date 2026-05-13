package com.lasertagnation.yachtmodule.repository;

import com.lasertagnation.yachtmodule.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
