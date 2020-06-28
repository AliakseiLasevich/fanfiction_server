package com.fanfiction.webproject.service.interfaces;

import com.fanfiction.webproject.entity.RoleEntity;

public interface RoleService {
    RoleEntity findByName(String role);
}
