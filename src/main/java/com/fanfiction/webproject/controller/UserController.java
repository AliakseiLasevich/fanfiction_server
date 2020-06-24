package com.fanfiction.webproject.controller;

import com.fanfiction.webproject.dto.UserDto;
import com.fanfiction.webproject.exceptions.UserServiceException;
import com.fanfiction.webproject.mappers.UserMapper;
import com.fanfiction.webproject.service.interfaces.UserService;
import com.fanfiction.webproject.ui.model.request.UserDetailsRequestModel;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import com.fanfiction.webproject.ui.model.response.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id) {
        UserDto userDto = userService.getUserByUserId(id);
        return UserMapper.INSTANCE.dtoToRest(userDto);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        if (userDetails.getEmail() == null || userDetails.getPassword() == null) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        UserDto userDto = UserMapper.INSTANCE.requestModelToDto(userDetails);
        UserDto createdUser = userService.createUser(userDto);
        return UserMapper.INSTANCE.dtoToRest(createdUser);
    }

    @PutMapping
    public String updateUser() {
        return "update";
    }

    @DeleteMapping
    public String deleteUser() {
        return "delete";
    }

}
