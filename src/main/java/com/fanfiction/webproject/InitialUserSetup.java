package com.fanfiction.webproject;

import com.fanfiction.webproject.entity.AuthorityEntity;
import com.fanfiction.webproject.entity.RoleEntity;
import com.fanfiction.webproject.entity.UserEntity;
import com.fanfiction.webproject.repository.AuthorityRepository;
import com.fanfiction.webproject.repository.RoleRepository;
import com.fanfiction.webproject.repository.UserRepository;
import com.fanfiction.webproject.security.Roles;
import com.fanfiction.webproject.utils.Utils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

@Component
public class InitialUserSetup {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final Utils utils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public InitialUserSetup(AuthorityRepository authorityRepository, RoleRepository roleRepository, UserRepository userRepository, Utils utils, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authorityRepository = authorityRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.utils = utils;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (checkAdminUserExist()) {
            return;
        }
        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");
        RoleEntity roleUser = createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));
        RoleEntity roleAdmin = createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
        createAdminUser(roleUser, roleAdmin);
    }

    private void createAdminUser(RoleEntity roleUser, RoleEntity roleAdmin) {
        UserEntity adminUser = new UserEntity();
        adminUser.setFirstName("Admin");
        adminUser.setLastName("Admin");
        adminUser.setNickName("Super-Admin-8000");
        adminUser.setEmail("admin@admin.com");
        adminUser.setEmailVerificationStatus(true);
        adminUser.setUserId(utils.generateRandomString(30));
        adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
        adminUser.setRoles(Arrays.asList(roleAdmin, roleUser));
        adminUser.setNonBlocked(true);
        adminUser.setDeleted(false);
        userRepository.save(adminUser);
    }

    private boolean checkAdminUserExist() {
        UserEntity admin = userRepository.findByEmail("admin@admin.com");
        return admin != null;
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
