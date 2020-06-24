package com.fanfiction.webproject.controller;

import com.fanfiction.webproject.dto.UserDto;
import com.fanfiction.webproject.mappers.UserMapper;
import com.fanfiction.webproject.service.interfaces.UserService;
import com.fanfiction.webproject.ui.model.request.UserDetailsRequestModel;
import com.fanfiction.webproject.ui.model.response.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getUser() {
        return "get user";
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
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
