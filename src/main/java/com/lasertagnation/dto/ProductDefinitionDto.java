package com.lasertagnation.dto;

import com.lasertagnation.model.NewProduct;
import com.lasertagnation.model.PressMachine;
import com.lasertagnation.model.ProductDefinition;
import com.lasertagnation.model.ProductGsm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDefinitionDto {
    private Long id;
    private String title;
    private Boolean status;
    private PressMachine pressMachine;
    private NewProductDto newProduct;
}
