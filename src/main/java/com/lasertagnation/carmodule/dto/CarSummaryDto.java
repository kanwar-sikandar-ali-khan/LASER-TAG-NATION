package com.lasertagnation.carmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarSummaryDto {

    private Long id;
    private String licensePlate;
    private String model;
}
