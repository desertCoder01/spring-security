package com.example.demo.model.repository;


import com.example.demo.model.entity.RoleInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<RoleInfo,String> {
    RoleInfo findByName(String roleName);
}
