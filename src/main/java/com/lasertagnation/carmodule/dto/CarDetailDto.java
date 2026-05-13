package com.lasertagnation.carmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDetailDto {

    private Long id;
    private String licensePlate;
    private String model;

    @Builder.Default
    private List<FoodDto> foods = new ArrayList<>();
}
