package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.dto.UserDto;
import com.fanfiction.webproject.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserDto(String email);

    UserDto getUserByUserId(String id);

    UserDto updateUser(UserDto userDto);

    void deleteUser(String id);

    List<UserDto> findAll();

    UserEntity getUserEntityByUserId(String userId);
}
