package com.fanfiction.webproject.ui.model.request;

import lombok.Data;

@Data
public class UserDetailsRequestModel {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Boolean active = true;
}
