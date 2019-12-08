package com.shanehonanie.service.impl;

import com.shanehonanie.io.entity.UserEntity;
import com.shanehonanie.io.repository.UserRepository;
import com.shanehonanie.service.UserService;
import com.shanehonanie.shared.Utils;
import com.shanehonanie.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Override
    public UserDto createUser(UserDto user) {
        // check if email already exists before saving
        if(userRepository.findUserByEmail(user.getEmail()) != null) throw new RuntimeException("Email already exists");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);

        // TODO : Implement encrypted password
        userEntity.setEncryptedPassword("test");

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }
}
