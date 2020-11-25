package com.intranet.project.service;

import com.intranet.project.controller.user.UserCreation;
import com.intranet.project.controller.user.ViewUser;
import com.intranet.project.exceptions.InternalServerErrorException;
import com.intranet.project.repository.post.PostingRepository;
import com.intranet.project.repository.user.UserEntity;
import com.intranet.project.repository.user.UserRepository;
import com.intranet.project.service.classes.UpdatePassword;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostingRepository postingRepository;


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

    public String updateUserPassword(UpdatePassword updatePassword){
        UserEntity userEntity = userRepository.getUserById(updatePassword.getId());
        if(userEntity.getPassword().equals(updatePassword.getCurrentPassword())){
            if(userRepository.updateUserPassword(userEntity.getId(), updatePassword.getNewPassword()) == 1){
                return "Password renewed";
            } else {
                throw new InternalServerErrorException("Changing password failed");
            }
        } else {
            return "Wrong password";
        }
    }

    public String deleteUserById(Long id){
        postingRepository.deleteUserPostings(id);
        if(userRepository.deleteUserById(id) == 1){
            return "User and user's posts removed";
        }
        return "Something went wrong";
    }
}
