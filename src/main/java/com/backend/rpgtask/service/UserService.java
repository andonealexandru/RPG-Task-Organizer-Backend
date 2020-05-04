package com.backend.rpgtask.service;

import com.backend.rpgtask.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUser(String email);
    UserDto getUserByUserId(String userId);
    UserDto updateUser(String userId, UserDto user);
    UserDto getUserByConfirmationToken(String confirmationToken);
    void deleteUser(String userId);
}
