package com.example.JournalApplication.repository;

import com.example.JournalApplication.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

 /* At Runtime , Springboot in itself injects the implementation of these repositories where this is used*/
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {

  /* Below is called Query Method DSL. There is no function definition of calling
  the MongoDb and writing select query to fetch from the table . In Springboot Query DSL ,
  you write the name of the function "findByUserName" such that "Username" in a field in database
  and Springboot understand that you are querying the MongoDb with UserName in input*/

  UserEntity findByUserName(String userName);

  void deleteByUserName(String name);
}
