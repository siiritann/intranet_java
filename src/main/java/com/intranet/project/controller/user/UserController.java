package com.intranet.project.controller.user;

import com.intranet.project.controller.classes.ResponseJSON;
import com.intranet.project.repository.user.UserEntity;
import com.intranet.project.service.UserService;
import com.intranet.project.service.classes.UpdatePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public Long createUser(@RequestBody UserCreation userCreation){
        return userService.createUser(userCreation);
    }

    @GetMapping("/list")
    public List<UserEntity> getListOfUsers() {
        return userService.getListOfUsers();
    }

    @GetMapping("/view/{id}")
    public ViewUser viewUser(@PathVariable("id") Long id) {
        return userService.viewUser(id);
    }

    @PutMapping("/update")
    public UserEntity updateUser(@RequestBody UserEntity userEntityUpdated){
        return userService.updateUser(userEntityUpdated);
    }

    @PutMapping("/update/password")
    public ResponseJSON updateUserPassword(@RequestBody UpdatePassword updatePassword){
        return new ResponseJSON(userService.updateUserPassword(updatePassword));
    }

    @DeleteMapping("/delete")
    public ResponseJSON deleteUserById(@RequestBody UserEntity userEntity){
        return new ResponseJSON(userService.deleteUserById(userEntity.getId()));
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserCreation userCreation){
        return userService.loginUser(userCreation);
    }
}
