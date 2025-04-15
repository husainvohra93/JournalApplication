package com.example.JournalApplication.controllers;

import com.example.JournalApplication.entity.UserEntity;
import com.example.JournalApplication.reposotirory.UserRepository;
import com.example.JournalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody UserEntity userEntity) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userFromDB = userService.findByUserName(userName);
        userFromDB.setUserName(userEntity.getUserName());
        userFromDB.setPassword(userEntity.getPassword());
        userService.saveNewEntry(userFromDB);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByID(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
