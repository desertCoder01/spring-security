package com.example.demo.model.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateRoleRequest {
    private String roleName;
    private String roleDescription;
    private List<String> permissions;
}
