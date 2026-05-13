package com.lasertagnation.carmodule.repository;

import com.lasertagnation.carmodule.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CarModule catalog foods — bean name {@code carFoodRepository} (distinct from YachtModule {@code foodRepository}).
 */
@Repository
public interface CarFoodRepository extends JpaRepository<Food, Long> {
}
