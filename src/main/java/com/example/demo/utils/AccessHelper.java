package com.example.demo.utils;


import com.example.demo.exception.InvalidRequestException;
import com.example.demo.model.constant.AppConstants;
import com.example.demo.model.entity.PermissionInfo;
import com.example.demo.model.entity.RoleInfo;
import com.example.demo.model.request.CreatePermissionRequest;
import com.example.demo.model.request.CreateRoleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AccessHelper {

    public void validateCreatePermissionRequest(List<CreatePermissionRequest> request) {


    }

    public PermissionInfo createPermissionInfo(String name, String description) {
        return PermissionInfo.builder()
                .id(UUID.randomUUID().toString().replace("-",""))
                .createdAt(AppConstants.CURRENT_TIME)
                .description(description)
                .name(name)
                .build();
    }

    public void validateCreateRoleRequest(CreateRoleRequest request) {
        if(ObjectUtils.isEmpty(request))
            throw new InvalidRequestException("Invalid request : Empty request");
        if(!StringUtils.hasText(request.getRoleName()))
            throw new InvalidRequestException("Invalid request : Empty role name provided");
        if(!StringUtils.hasText(request.getRoleDescription()))
            throw new InvalidRequestException("Invalid request : Empty role description provided");

    }

    public RoleInfo buildRoleInfo(CreateRoleRequest request) {
        return RoleInfo.builder()
                .createdAt(AppConstants.CURRENT_TIME)
                .description(request.getRoleDescription())
                .name(request.getRoleName().toUpperCase())
                .permissions(request.getPermissions()
                        .stream().map(permit -> permit.toUpperCase())
                        .collect(Collectors.toList()))
                .id(UUID.randomUUID().toString().replace("-", ""))
                .build();
    }
}
