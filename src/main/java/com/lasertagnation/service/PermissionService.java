package com.lasertagnation.service;

import com.lasertagnation.dto.PermissionDto;

import java.util.List;

public interface PermissionService {
    List<PermissionDto> getAll();
    PermissionDto findById(Long id);
}
