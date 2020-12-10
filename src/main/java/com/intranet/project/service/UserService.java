package com.intranet.project.service;

import com.intranet.project.controller.user.UserCreation;
import com.intranet.project.controller.user.ViewUser;
import com.intranet.project.exceptions.BadRequestException;
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

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
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
        if(userRepository.getListOfUsers().isEmpty()){
            UserEntity userEntity = new UserEntity(username, password, email, true);
            return userRepository.createUser(userEntity);
        } else {
            UserEntity userEntity = new UserEntity(username, password, email);
            return userRepository.createUser(userEntity);
        }

    }

    public List<UserEntity> getListOfUsers(){
        return userRepository.getListOfUsers();
    }

    public List<String> getListOfUsernames() {
        return userRepository.getListOfUsernames();
    }

    public ViewUser viewUser(Long id) {
        UserEntity userEntity = userRepository.viewUser(id);
        String username = userEntity.getUsername();
        String email = userEntity.getEmail();
        String firstname = userEntity.getFirstName();
        String lastname = userEntity.getLastName();
        Date birthdate = userEntity.getBirthDate();
        String phone = userEntity.getPhone();
        return new ViewUser(id, username, email, firstname, lastname, birthdate, phone);
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
            throw new InternalServerErrorException("Wrong password");
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
        Boolean isAdmin = userRepository.checkIfAdmin(id);
        Jwt jwt = new Jwt();
        return jwt.getBearerToken(id, username, isAdmin);
    }

    public boolean validate(String username, String rawPassword){
        String encodedPassword = userRepository.getUserEntityByUsername(username).getPassword();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String savePassword(String password){
        return passwordEncoder.encode(password);
    }

    public void getImageById(Long userId, HttpServletResponse response) throws IOException, SQLException {
        byte[] test = userRepository.getImageById(userId);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + "picture.jpg" + "\"");
        response.setContentType("image/jpg");
        // TODO baasi salvestada fail size ja byte arraysse lugeda faili täpne suurus
        response.setContentLength(test.length);
        response.getOutputStream().write(test);
    }

    public void postImage(byte[] bytes, long userId) {
        userRepository.postImage(bytes, userId);
    }

    public String deleteImages(Long id) {
        return userRepository.deleteImages(id);
    }
    public Long getUserIdByEmail(String email){
        return userRepository.getUserIdByEmail(email);
    }

    public void updateUserRole(Long adminId, Long userId) {
        if (adminId.equals(userId)) {
            throw new BadRequestException("Can't change role for yourself");
        } else if (!userRepository.checkIfAdmin(adminId)) {
            throw new BadRequestException("Not enough permissions");
        } else if (userRepository.checkIfAdmin(userId)) {
            throw new BadRequestException("This user already is admin");
        } else {
            userRepository.updateUserRole(userId);
        }
    }

}
