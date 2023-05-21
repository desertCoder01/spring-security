package com.example.demo.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(value = "user_info")
public class UserInfo {

    @Id
    private String id;
    private String email;
    private String name;
    private String password;
    private String role;
    private boolean active;
    private List<String> permissions;
    private long createdAt;
    private long updatedAt;
}
