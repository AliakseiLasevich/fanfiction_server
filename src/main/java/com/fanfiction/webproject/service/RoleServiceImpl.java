package com.fanfiction.webproject.service;

import com.fanfiction.webproject.entity.RoleEntity;
import com.fanfiction.webproject.repository.RoleRepository;
import com.fanfiction.webproject.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public RoleEntity findByName(String role) {
        return roleRepository.findByName(role);
    }
}
