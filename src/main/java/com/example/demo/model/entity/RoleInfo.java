package com.example.demo.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(value = "role_info")
public class RoleInfo {

    @Id
    private String id;
    private String name;
    private String description;
    private long createdAt;
    private long updatedAt;
    private List<String> permissions;
}
