package com.example.JournalApplication.controllers;

import com.example.JournalApplication.api.response.WeatherResponse;
import com.example.JournalApplication.entity.UserEntity;
import com.example.JournalApplication.repository.UserRepository;
import com.example.JournalApplication.repository.UserRepositoryImpl;
import com.example.JournalApplication.service.UserService;
import com.example.JournalApplication.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private UserRepositoryImpl userRepositoryimpl;

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

    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting = "";
        if(weatherResponse!=null){
            greeting =  ". Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greeting,HttpStatus.OK);
    }

    @GetMapping("/checkCriteriaUsers")
    public ResponseEntity<?> getCriteriaUsers() {
        List<UserEntity> allUsers = userRepositoryimpl.getUsersForSA();
        if (allUsers != null && !allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
