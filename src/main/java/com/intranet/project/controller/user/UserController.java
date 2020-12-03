package com.intranet.project.controller.user;

import com.intranet.project.controller.classes.ResponseJSON;
import com.intranet.project.repository.user.UserEntity;
import com.intranet.project.security.MyUser;
import com.intranet.project.service.UserService;
import com.intranet.project.service.classes.UpdatePassword;
import javassist.bytecode.ByteArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
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

    @PostMapping("/image/post/{userId}")
    public void postImage(@PathVariable("userId") String userId,
                          @RequestParam("photos") MultipartFile file) throws IOException{
        try {
            byte[] bytes = file.getBytes();
            userService.postImage(bytes);
        } catch(IOException e){
            System.out.println("got IOException");
            System.out.println(e);
        }
        //MyUser userDetails = (MyUser) authentication.getPrincipal();
        System.out.println("Got something");

    }

    @GetMapping("/image/{userId}")
    public Blob getImageById(@PathVariable("userId") Long userId){
        return userService.getImageById(userId);
    }

    @GetMapping("/view/basic")
    public BasicUser basicUser(Authentication authentication) {
        MyUser userDetails = (MyUser) authentication.getPrincipal();
        BasicUser basicUser = new BasicUser(userDetails.getId(), userDetails.getUsername());
        return basicUser;
    }

    @GetMapping("/view")
    public ViewUser viewUser(Authentication authentication) {
        MyUser userDetails = (MyUser) authentication.getPrincipal();
        System.out.println(userDetails.getId());
        System.out.println(userDetails.getUsername());
        return userService.viewUser(userDetails.getId());
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
