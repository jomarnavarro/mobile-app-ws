package com.testautomationcoach.app.ws.service.impl;

import com.testautomationcoach.app.ws.UserRepository;
import com.testautomationcoach.app.ws.io.entity.UserEntity;
import com.testautomationcoach.app.ws.service.UserService;
import com.testautomationcoach.app.ws.shared.Utils;
import com.testautomationcoach.app.ws.shared.dto.UserDto;
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
        UserEntity storedUserDetails = userRepository.findByEmail(user.getEmail());

        if(storedUserDetails != null) {
            throw new RuntimeException("Record already exists");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setEncryptedPassword("test");
        userEntity.setUserId(utils.generateUserId());

         UserEntity savedUser = userRepository.save(userEntity);

        UserDto retValue = new UserDto();

        BeanUtils.copyProperties(savedUser, retValue);

        return retValue;
    }
}
