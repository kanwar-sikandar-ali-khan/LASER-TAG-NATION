package com.lasertagnation.service;

import com.lasertagnation.dto.UserDto;
import com.lasertagnation.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User registerUser(User user);
    List<UserDto> getAll();
    List<UserDto> getUsersByRole(String role);
    UserDto findById(Long id);
    UserDto updateUser(Long id, UserDto userDto);
    void deleteById(Long id);
}
