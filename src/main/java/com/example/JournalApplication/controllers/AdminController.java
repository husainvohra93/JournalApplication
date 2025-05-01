package com.example.JournalApplication.controllers;

import com.example.JournalApplication.cache.AppCache;
import com.example.JournalApplication.entity.UserEntity;
import com.example.JournalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserEntity> allUsers = userService.getAll();
        if (allUsers != null && !allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /* For using this , you need to first crate a normal user and add the Role "ADMIN" to it from the backend MongoDb */
    @PostMapping("/create-admin-user")
    public void createAdminUser(@RequestBody UserEntity user){

        userService.saveAdmin(user);
    }

    /* This function is used to clear the AppCache dynamically by using a Webservice call */
    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
}
