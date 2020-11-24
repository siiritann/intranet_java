package com.intranet.project.controller;

import com.intranet.project.controller.user.UserCreation;
import com.intranet.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    // 1 - CREATE ACCOUNT
    @PostMapping("/createuser")
    public Long createUser(@RequestBody UserCreation userCreation){
        return userService.createUser(userCreation);
    }

}
