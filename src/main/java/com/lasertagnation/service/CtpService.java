package com.lasertagnation.service;

import com.lasertagnation.dto.CtpDto;

import java.util.List;

public interface CtpService {
    List<CtpDto> getAll();
    CtpDto getById(Long id);
    CtpDto save(CtpDto ctpDto);
    String deleteById(Long id);
    CtpDto update(Long id, CtpDto ctpDto);
}
