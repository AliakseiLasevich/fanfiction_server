package com.fanfiction.webproject;

import com.fanfiction.webproject.entity.AuthorityEntity;
import com.fanfiction.webproject.entity.RoleEntity;
import com.fanfiction.webproject.entity.UserEntity;
import com.fanfiction.webproject.repository.AuthorityRepository;
import com.fanfiction.webproject.repository.RoleRepository;
import com.fanfiction.webproject.repository.UserRepository;
import com.fanfiction.webproject.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

@Component
public class InitialUserSetup {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utils utils;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

        RoleEntity roleUser = createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));
        RoleEntity roleAdmin = createRole("ROLE_ADMIN", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

        if (roleAdmin == null) {
            return;
        }

        UserEntity adminuser = new UserEntity();
        adminuser.setFirstName("Admin");
        adminuser.setLastName("Admin");
        adminuser.setEmail("Admin");
        adminuser.setEmailVerificationStatus(true);
        adminuser.setUserId(utils.generateRandomString(30));
        adminuser.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
        adminuser.setRoles(Arrays.asList(roleAdmin, roleUser));
//        userRepository.save(adminuser);
    }


    private AuthorityEntity createAuthority(String name) {
        AuthorityEntity authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {
        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }

}
