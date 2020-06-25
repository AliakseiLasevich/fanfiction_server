package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUser(String email);

    UserDto getUserByUserId(String id);

    UserDto updateUser(UserDto userDto);

    void deleteUser(String id);
}
