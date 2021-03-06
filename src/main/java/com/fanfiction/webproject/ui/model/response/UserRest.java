package com.fanfiction.webproject.ui.model.response;

import com.fanfiction.webproject.entity.RoleEntity;
import lombok.Data;

import java.util.Collection;

@Data
public class UserRest {

    private String userId;
    private String nickName;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean nonBlocked;
    private Collection<RoleEntity> roles;


}
