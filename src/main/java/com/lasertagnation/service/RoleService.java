package com.lasertagnation.service;


import com.lasertagnation.dto.RoleDto;
import com.lasertagnation.model.Role;

import java.util.List;

public interface RoleService {

    RoleDto addRole(RoleDto roleDto);
    List<RoleDto> getAll();
    RoleDto findById(Long id);
    RoleDto updateRole(Long id, RoleDto roleDto);

}
