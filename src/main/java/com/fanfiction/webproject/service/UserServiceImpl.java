package com.fanfiction.webproject.service;

import com.fanfiction.webproject.dto.UserDto;
import com.fanfiction.webproject.entity.UserEntity;
import com.fanfiction.webproject.exceptions.UserServiceException;
import com.fanfiction.webproject.mappers.UserMapper;
import com.fanfiction.webproject.repository.UserRepository;
import com.fanfiction.webproject.service.interfaces.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.fanfiction.webproject.utils.Utils;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final Utils utils;
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Utils utils, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.utils = utils;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) != null)
            throw new UserServiceException("Record already exists");
        UserEntity userEntity = UserMapper.INSTANCE.dtoToEntity(userDto);
        userEntity.setUserId(utils.generateUserId(30));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        UserEntity storedUserDetails = userRepository.save(userEntity);
        return UserMapper.INSTANCE.entitytoDto(storedUserDetails);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(email);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }
}
