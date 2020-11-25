package com.intranet.project.service;

import com.intranet.project.controller.user.UserCreation;
import com.intranet.project.controller.user.ViewUser;
import com.intranet.project.repository.user.UserEntity;
import com.intranet.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public Long createUser(UserCreation userCreation){
        String username = userCreation.getUsername();
        String password = userCreation.getPassword();
        String email = userCreation.getEmail();
        UserEntity userEntity = new UserEntity(username, password, email);
        return userRepository.createUser(userEntity);
    }

    public List getListOfUsers(){
        return userRepository.getListOfUsers();
    }

    public ViewUser viewUser(Long id) {
        UserEntity userEntity = userRepository.viewUser(id);
        String username = userEntity.getUsername();
        String password = userEntity.getPassword();
        String email = userEntity.getEmail();
        String firstname = userEntity.getFirstName();
        String lastname = userEntity.getLastName();
        Date birthdate = userEntity.getBirthDate();
        String phone = userEntity.getPhone();
        ViewUser viewUser = new ViewUser(username, password, email, firstname, lastname, birthdate, phone);
        return viewUser;
    }
}
