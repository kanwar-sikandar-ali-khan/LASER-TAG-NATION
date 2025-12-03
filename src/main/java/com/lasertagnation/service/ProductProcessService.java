package com.lasertagnation.service;

import com.lasertagnation.dto.ProductProcessDto;
import com.lasertagnation.model.ProductProcess;

import java.util.List;

public interface ProductProcessService
{
    ProductProcessDto save(ProductProcessDto productProcessDto);
    List<ProductProcessDto> getAll();
    ProductProcessDto findById(Long id);
    ProductProcessDto findByName(String name);
    List<ProductProcessDto> searchByName(String name);
    String deleteById(Long id);
    ProductProcessDto updateProductProcess(Long id, ProductProcess productProcess);
}
