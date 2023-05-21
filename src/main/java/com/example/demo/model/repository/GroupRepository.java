package com.example.demo.model.repository;


import com.example.demo.model.entity.GroupInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<GroupInfo,String> {
}
