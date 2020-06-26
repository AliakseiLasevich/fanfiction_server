package com.fanfiction.webproject.controller;

import com.fanfiction.webproject.dto.UserDto;
import com.fanfiction.webproject.exceptions.UserServiceException;
import com.fanfiction.webproject.mappers.UserMapper;
import com.fanfiction.webproject.service.interfaces.UserService;
import com.fanfiction.webproject.ui.model.request.UserDetailsRequestModel;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import com.fanfiction.webproject.ui.model.response.OperationStatusModel;
import com.fanfiction.webproject.ui.model.response.RequestOperationStatus;
import com.fanfiction.webproject.ui.model.response.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/")
    public List<UserRest> allUsers() {
        List<UserDto> userDtos = userService.findAll();
        return userDtos.stream()
                .map(UserMapper.INSTANCE::dtoToRest)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id) {
        UserDto userDto = userService.getUserByUserId(id);
        return UserMapper.INSTANCE.dtoToRest(userDto);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        if (userDetails.getEmail() == null || userDetails.getPassword() == null) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        UserDto userDto = UserMapper.INSTANCE.requestModelToDto(userDetails);
        UserDto createdUser = userService.createUser(userDto);
        return UserMapper.INSTANCE.dtoToRest(createdUser);
    }

    @PutMapping(path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
        if (userDetails.getEmail() == null || userDetails.getPassword() == null) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        UserDto userDto = UserMapper.INSTANCE.requestModelToDto(userDetails);
        userDto.setUserId(id);
        UserDto updatedUser = userService.updateUser(userDto);
        return UserMapper.INSTANCE.dtoToRest(updatedUser);
    }

    @DeleteMapping(path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatusModel deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.name());
        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return operationStatusModel;
    }

    @GetMapping(path = "/email-verification", produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());
        boolean isVerified = userService.verifyEmailToken(token);
        if (isVerified) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } else {
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }

        return returnValue;
    }

}
