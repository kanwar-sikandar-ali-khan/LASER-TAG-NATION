package com.lasertagnation.service;

import com.lasertagnation.dto.PaperSizeDto;
import com.lasertagnation.dto.PressMachineDto;

import java.util.List;

public interface PaperSizeService
{
    PaperSizeDto save(PaperSizeDto paperSizeDto);
    List<PaperSizeDto> getAll();
    PaperSizeDto findById(Long id) throws Exception;
    PaperSizeDto findByLabel(String label);
    List<PaperSizeDto> searchByLabel(String label);
    String deleteById(Long id);
    PaperSizeDto updatePaperSize(Long id, PaperSizeDto paperSizeDto);
}
