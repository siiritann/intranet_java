package com.intranet.project.service;

import com.intranet.project.controller.user.UserCreation;
import com.intranet.project.repository.user.UserEntity;
import com.intranet.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // 1 - CREATE ACCOUNT
    public Long createUser(UserCreation userCreation){
        String username = userCreation.getUsername();
        String password = userCreation.getPassword();
        String email = userCreation.getEmail();
        UserEntity userEntity = new UserEntity(username, password, email);
        return userRepository.createUser(userEntity);
    }
}
