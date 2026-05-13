package com.lasertagnation.carmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarBatchLoadRowDto {

    private Long id;
    private String licensePlate;
    private int foodCount;
}
