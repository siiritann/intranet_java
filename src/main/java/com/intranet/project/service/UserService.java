package com.intranet.project.service;

import com.intranet.project.controller.user.UserCreation;
import com.intranet.project.controller.user.ViewUser;
import com.intranet.project.exceptions.InternalServerErrorException;
import com.intranet.project.exceptions.NotFoundException;
import com.intranet.project.repository.post.PostingRepository;
import com.intranet.project.repository.user.UserEntity;
import com.intranet.project.repository.user.UserRepository;
import com.intranet.project.security.Jwt;
import com.intranet.project.service.classes.UpdatePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.util.regex.Matcher;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private String emailPattern = "^(.+)@(.+)$";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Long createUser(UserCreation userCreation){
        String username = userCreation.getUsername();
        String password = savePassword(userCreation.getPassword());
        String email = userCreation.getEmail();
        UserEntity userEntity = new UserEntity(username, password, email);
        return userRepository.createUser(userEntity);
    }

    public List<UserEntity> getListOfUsers(){
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
        return new ViewUser(id, username, password, email, firstname, lastname, birthdate, phone);
    }

    public UserEntity updateUser(UserEntity userEntityUpdated){
        if(!userEntityUpdated.getEmail().matches(emailPattern)) {
            throw new InternalServerErrorException("Invalid email");
        }
        if(userRepository.updateUser(userEntityUpdated) == 1){
            return userRepository.getUserById(userEntityUpdated.getId());
        } else {
            throw new NotFoundException("User not found");
        }

    }

    public String updateUserPassword(UpdatePassword updatePassword){
        UserEntity userEntity = userRepository.getUserById(updatePassword.getId());
        if(validate(userEntity.getUsername(), updatePassword.getCurrentPassword())){
            if(userRepository.updateUserPassword(userEntity.getId(), savePassword(updatePassword.getNewPassword())) == 1){
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

    public String loginUser(UserCreation userCreation){
        String username = userCreation.getUsername();
        String password = userCreation.getPassword();
        Long id = userRepository.getUserIdByUsername(username);
        if (id == null) {
            throw new NotFoundException("Username not found");
        }

        if (!validate(username, password)){
            throw new NotFoundException("Wrong password");
        }
        Jwt jwt = new Jwt();
        return jwt.getBearerToken(id, username);
    }

    public boolean validate(String username, String rawPassword){
        String encodedPassword = userRepository.getUserEntityByUsername(username).getPassword();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String savePassword(String password){
        return passwordEncoder.encode(password);
    }

//    public Blob getImageById(Long userId) {
//        System.out.println(userRepository.getImageById(userId));
//        return
//
//    }

    public void postImage(byte[] bytes, long userId) {
        userRepository.postImage(bytes, userId);
    }
}
