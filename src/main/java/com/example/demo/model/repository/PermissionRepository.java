package com.example.demo.model.repository;


import com.example.demo.model.entity.PermissionInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface PermissionRepository extends MongoRepository<PermissionInfo,String> {

    List<PermissionInfo> findAllByName(Set<String> permitNames);
}
