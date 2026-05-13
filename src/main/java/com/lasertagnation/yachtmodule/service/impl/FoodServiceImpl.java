package com.lasertagnation.yachtmodule.service.impl;

import com.lasertagnation.yachtmodule.entity.Food;
import com.lasertagnation.yachtmodule.repository.FoodRepository;
import com.lasertagnation.yachtmodule.service.FoodService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    @Override
    public Page<Food> findAll(Pageable pageable) {
        return foodRepository.findAll(pageable);
    }

    @Override
    public Food findById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Food save(Food food) {
        return foodRepository.save(food);
    }

    /**
     * With {@code ManyToMany(cascade=ALL)} on {@link com.lasertagnation.yachtmodule.entity.Yacht}, deleting a food
     * that is still linked in {@code yacht_food} can fail or orphan join rows depending on FK direction — another
     * cascade foot-gun to observe in SQL logs.
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        foodRepository.deleteById(id);
    }
}
