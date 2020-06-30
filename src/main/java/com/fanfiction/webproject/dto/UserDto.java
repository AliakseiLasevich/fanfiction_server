package com.fanfiction.webproject.dto;

import com.fanfiction.webproject.entity.RoleEntity;
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
    private Collection<RoleEntity> roles;
}
