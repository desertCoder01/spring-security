package com.example.demo.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class CreateRoleResponse {
    private String roleId;
    private String roleName;
    private List<String> permissionsAssigned;
    private Map<String, String> errors;
}
