package com.example.demo.model.repository;


import com.example.demo.model.entity.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserInfo,String> {

    UserInfo findByEmail(String email);
}
