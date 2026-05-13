package com.lasertagnation.service;

import com.lasertagnation.dto.InventoryDto;
import com.lasertagnation.dto.PaperMarketRatesDto;
import com.lasertagnation.dto.ProductRuleDto;

import java.util.List;

public interface InventoryService {
    InventoryDto save(InventoryDto inventoryDto);
    List<InventoryDto> getAll();
    List<InventoryDto> searchByPaperStock(String paperStock);
    InventoryDto findById(Long id);
    String deleteById(Long id);
    InventoryDto updateInventory(Long id, InventoryDto inventoryDto);
    PaperMarketRatesDto updatePaperMarketRate(Long inventoryId);
}
