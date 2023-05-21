package com.example.demo.model.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateGroupResponse {
    private String groupId;
    private String name;
    private String description;
    private String superAdmin;
    private boolean memberPostAllowed;
    private List<String> initialMembers;
}
