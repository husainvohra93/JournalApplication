package com.example.JournalApplication.repository;

import com.example.JournalApplication.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    MongoTemplate mongoTemplate;


    /* getUsersForSA means getUsersForSentimentAnalysis*/
    public List<UserEntity> getUsersForSA() {
        Query query = new Query();

        Criteria criteria = new Criteria();

        query.addCriteria(criteria.andOperator(
                Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"),
                Criteria.where("sentimentAnalysis").is(true),
                Criteria.where("roles").in("USER","ADMIN")
        ));

        List<UserEntity> users = mongoTemplate.find(query, UserEntity.class);

        return users;
    }
 }
