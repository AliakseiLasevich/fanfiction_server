package com.fanfiction.webproject.ui.model.request;

import lombok.Data;

import java.util.Collection;

@Data
public class UserDetailsRequestModel {
    private String email;
    private String nickName;
    private String firstName;
    private String lastName;
    private String password;
    private Boolean nonBlocked = true;
    private Collection<String> rolesNames;
}
