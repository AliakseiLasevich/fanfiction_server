package com.fanfiction.webproject.service;

import com.fanfiction.webproject.dto.UserDto;
import com.fanfiction.webproject.entity.RoleEntity;
import com.fanfiction.webproject.entity.UserEntity;
import com.fanfiction.webproject.exceptions.UserServiceException;
import com.fanfiction.webproject.mappers.UserMapper;
import com.fanfiction.webproject.repository.UserRepository;
import com.fanfiction.webproject.security.Roles;
import com.fanfiction.webproject.security.UserPrincipal;
import com.fanfiction.webproject.service.interfaces.EmailService;
import com.fanfiction.webproject.service.interfaces.RoleService;
import com.fanfiction.webproject.service.interfaces.UserService;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import com.fanfiction.webproject.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Utils utils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Utils utils, BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService,
                           RoleService roleService) {
        this.userRepository = userRepository;
        this.utils = utils;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto, HttpServletRequest request) {
        if (userRepository.findByEmail(userDto.getEmail()) != null)
            throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        UserEntity userEntity = UserMapper.INSTANCE.dtoToEntity(userDto);
        userEntity.setUserId(utils.generateRandomString(30));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        String emailVerificationToken = utils.generateEmailVerificationToken(userDto.getEmail());
        userEntity.setEmailVerificationToken(emailVerificationToken);

        Collection<RoleEntity> roleEntities = getUserRole();
        userEntity.setRoles(roleEntities);

        UserEntity storedUserDetails = userRepository.save(userEntity);
        emailService.sendVerificationEmailToken(userDto.getEmail(), emailVerificationToken, request);
        return UserMapper.INSTANCE.entityToDto(storedUserDetails);
    }

    private Collection<RoleEntity> getUserRole() {
        Collection<RoleEntity> roleEntities = new HashSet<>();
        RoleEntity roleEntity = roleService.findByName(Roles.ROLE_USER.name());
        if (roleEntity != null) {
            roleEntities.add(roleEntity);
        }
        return roleEntities;
    }


    @Override
    public List<UserDto> findAll() {
        List<UserEntity> userEntities = userRepository.findAll().stream()
                .filter(userEntity -> !userEntity.getDeleted())
                .collect(Collectors.toList());
        if (userEntities.size() == 0) {
            throw new UserServiceException(ErrorMessages.NO_RECORDS_IN_BASE.getErrorMessage());
        }
        return userEntities.stream()
                .map(UserMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return new UserPrincipal(userEntity);
    }

    @Override
    public UserDto getUserDto(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        return UserMapper.INSTANCE.entityToDto(userEntity);
    }

    @Override
    public UserDto getUserByUserId(String id) {
        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null) {
            throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return UserMapper.INSTANCE.entityToDto(userEntity);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if (userRepository.findByUserId(userDto.getUserId()) == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserEntity userEntity = userRepository.findByUserId(userDto.getUserId());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setNonBlocked(userDto.getNonBlocked());
        String dtoPassword = userDto.getPassword();
        if (dtoPassword != null) {
            userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        }
        if (userDto.getRolesNames() != null) {
            userEntity.setRoles(userDto.getRolesNames().stream()
                    .map(roleService::findByName).collect(Collectors.toList()));
        }
        UserEntity storedUserDetails = userRepository.save(userEntity);
        return UserMapper.INSTANCE.entityToDto(storedUserDetails);
    }

    @Override
    public void deleteUser(String id) {
        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        userEntity.setDeleted(true);
        userRepository.save(userEntity);
    }


    @Override
    public UserEntity getUserEntityByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return userEntity;
    }

    @Override
    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;
        UserEntity userEntity = userRepository.findUserByEmailVerificationToken(token);
        if (userEntity != null) {
            userEntity.setEmailVerificationToken(null);
            userEntity.setEmailVerificationStatus(Boolean.TRUE);
            userRepository.save(userEntity);
            returnValue = true;
        }
        return returnValue;
    }
}