package com.lasertagnation.service;

import com.lasertagnation.dto.PaperSizeDto;
import com.lasertagnation.dto.PressMachineDto;

import java.util.List;

public interface PressMachineService
{
    PressMachineDto save(PressMachineDto pressMachineDto);
    List<PressMachineDto> getAll();
    PressMachineDto findById(Long id);
    PressMachineDto findByName(String name);
    String findVendorByPressMachine(String name);
    List<String> findDistinctNames();
    List<PressMachineDto> searchByName(String name);
    List<PressMachineDto> getPressMachineByPaperSizeId(Long paperSizeId);
    String deleteById(Long id);
    PressMachineDto updatePressMachine(Long id, PressMachineDto pressMachineDto);
    void deletePressMachineSizeById(Long id, Long pressMachineSizeId);
}
