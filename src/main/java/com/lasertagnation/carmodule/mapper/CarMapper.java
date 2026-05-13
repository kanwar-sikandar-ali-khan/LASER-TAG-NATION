package com.lasertagnation.carmodule.mapper;

import com.lasertagnation.carmodule.dto.CarBatchLoadRowDto;
import com.lasertagnation.carmodule.dto.CarDetailDto;
import com.lasertagnation.carmodule.dto.CarSummaryDto;
import com.lasertagnation.carmodule.dto.FoodDto;
import com.lasertagnation.carmodule.entity.Car;
import com.lasertagnation.carmodule.entity.Food;
import com.lasertagnation.carmodule.repository.CarRepository;

import java.util.stream.Collectors;

public final class CarMapper {

    private CarMapper() {
    }

    public static FoodDto toFoodDto(Food food) {
        if (food == null) {
            return null;
        }
        return FoodDto.builder()
                .id(food.getId())
                .name(food.getName())
                .category(food.getCategory())
                .build();
    }

    public static CarDetailDto toDetailDto(Car car) {
        if (car == null) {
            return null;
        }
        return CarDetailDto.builder()
                .id(car.getId())
                .licensePlate(car.getLicensePlate())
                .model(car.getModel())
                .foods(car.getFoods().stream().map(CarMapper::toFoodDto).collect(Collectors.toList()))
                .build();
    }

    public static CarSummaryDto toSummaryDto(CarRepository.CarSummaryProjection projection) {
        if (projection == null) {
            return null;
        }
        return CarSummaryDto.builder()
                .id(projection.getId())
                .licensePlate(projection.getLicensePlate())
                .model(projection.getModel())
                .build();
    }

    public static CarBatchLoadRowDto toBatchRowDto(Car car, int foodCount) {
        return CarBatchLoadRowDto.builder()
                .id(car.getId())
                .licensePlate(car.getLicensePlate())
                .foodCount(foodCount)
                .build();
    }
}
