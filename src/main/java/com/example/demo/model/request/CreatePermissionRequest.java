package com.example.demo.model.request;

import lombok.Data;

@Data
public class CreatePermissionRequest {
    private String permissionName;
    private String permissionDescription;
}
