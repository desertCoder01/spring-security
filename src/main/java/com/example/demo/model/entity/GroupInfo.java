package com.example.demo.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(value = "group_info")
public class GroupInfo {

    @Id
    private String id;
    private String name;
    private String superAdmin;
    private String description;
    private String groupImageUrl;
    private List<String> members;
    private List<String> admins;
    private boolean memberPostAllowed;
    private long createdAt;
    private long updatedAt;
    private String updatedBy;
}
