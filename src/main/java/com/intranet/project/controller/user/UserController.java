package com.intranet.project.controller.user;

import com.intranet.project.controller.classes.ResponseJSON;
import com.intranet.project.repository.user.UserEntity;
import com.intranet.project.security.MyUser;
import com.intranet.project.service.ResetPasswordService;
import com.intranet.project.service.UserService;
import com.intranet.project.service.classes.UpdatePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/create")
    public Long createUser(@RequestBody UserCreation userCreation){
        return userService.createUser(userCreation);
    }

    @GetMapping("/list")
    public List<UserEntity> getListOfUsers() {
        return userService.getListOfUsers();
    }

    @GetMapping("/list/usernames")
    public List<String> getListOfUsernames() {
        return userService.getListOfUsernames();
    }

    @GetMapping("/view/{id}")
    public ViewUser viewUser(@PathVariable("id") Long id) {
        return userService.viewUser(id);
    }

    @PostMapping("/image/post")
    public void postImage(@RequestParam("photos") MultipartFile file, Authentication authentication) throws IOException{
        try {
            byte[] bytes = file.getBytes();
            MyUser userDetails = (MyUser) authentication.getPrincipal();
            userService.postImage(bytes, userDetails.getId());
        } catch(IOException e){
            System.out.println("got IOException");
            System.out.println(e);
        }
    }

    @GetMapping("/image/{userId}")
    public void getImageById(@PathVariable("userId") Long userId,
                             HttpServletResponse response) throws IOException, SQLException {

       userService.getImageById(userId, response);
    }

    @GetMapping("/image")
    public void getImage(HttpServletResponse response, Authentication authentication) throws IOException, SQLException {
        MyUser userDetails = (MyUser) authentication.getPrincipal();
        Long userId = userDetails.getId();
        userService.getImageById(userId, response);
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

    @DeleteMapping("/delete/{id}")
    public ResponseJSON deleteUserById(@PathVariable("id") long id){
        return new ResponseJSON(userService.deleteUserById(id));
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserCreation userCreation){
        return userService.loginUser(userCreation);
    }

    @DeleteMapping("/image/delete")
    public ResponseJSON deleteImages(Authentication authentication){
        MyUser userDetails = (MyUser) authentication.getPrincipal();
        return new ResponseJSON(userService.deleteImages(userDetails.getId()));
    }


    @PostMapping("/forgotpw")
    public void forgotPassword(@RequestParam("email") String email) throws MessagingException {
        String uuid = UUID.randomUUID().toString();
        resetPasswordService.saveUUIDToBase(userService.getUserIdByEmail(email), uuid);
        String url = "http://localhost:8081/#/resetpw?q=" + uuid;
        resetPasswordService.sendEmail(resetPasswordService.createSession(), email, url);
    }

    @PostMapping("/resetpw")
    public String resetPassword(@RequestParam("q") String uuid,
                                @RequestBody String password){
        return resetPasswordService.resetUserPw(uuid, password);
    }

}
