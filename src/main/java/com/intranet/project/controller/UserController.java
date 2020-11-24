package com.intranet.project.controller;

import com.intranet.project.controller.user.UserCreation;
import com.intranet.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/create/user")
    public Long createUser(@RequestBody UserCreation userCreation){
        return userService.createUser(userCreation);
    }

    @GetMapping("userslist")
    public List getListOfUsers() {
        return userService.getListOfUsers();
    }
}
