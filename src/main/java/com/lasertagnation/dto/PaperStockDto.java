package com.lasertagnation.dto;

import com.lasertagnation.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaperStockDto {
    private Long id;
    private String name;
    private Boolean status;
    private List<Brand> brands;
}
