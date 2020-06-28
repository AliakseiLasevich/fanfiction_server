package com.fanfiction.webproject.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class UserDto implements Serializable {

    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;
    private Boolean active;
    private Collection<String> roles;
}
