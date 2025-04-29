package com.example.JournalApplication.service;

/*Business Logic is written in Service */

import com.example.JournalApplication.entity.UserEntity;
import com.example.JournalApplication.reposotirory.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(UserEntity user) {
        userRepository.save(user);
    }

    public void saveNewEntry(UserEntity userEntity) throws Exception {
        try {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setRoles(List.of("USER"));
            userRepository.save(userEntity);
        }catch (Exception e){
            log.error("In Exception in {} block of {}", "UserService" , userEntity.getUserName());
            log.info("Error occurred for {} : " , userEntity.getUserName(), e);
            throw e;
        }
    }

    public List<UserEntity> getAll() {

        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(ObjectId userId) {

        return userRepository.findById(userId);
    }

    public void deleteById(ObjectId userId) {

        userRepository.deleteById(userId);
    }

    public UserEntity findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void saveAdmin(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(userEntity);
    }
}
