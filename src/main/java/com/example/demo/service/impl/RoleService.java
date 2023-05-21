package com.example.demo.service.impl;

import com.example.demo.model.entity.RoleInfo;
import com.example.demo.model.repository.RoleRepository;
import com.example.demo.model.request.CreateRoleRequest;
import com.example.demo.model.response.CreateRoleResponse;
import com.example.demo.model.response.GenericApiResponse;
import com.example.demo.service.IRoleService;
import com.example.demo.utils.AccessHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleService implements IRoleService {

    private final AccessHelper accessHelper;
    private final RoleRepository roleRepository;

    @Override
    public GenericApiResponse<CreateRoleResponse> createRole(CreateRoleRequest request) {
        accessHelper.validateCreateRoleRequest(request);
        Map<String,String> errorMap = new HashMap<>();
        RoleInfo roleInfo = accessHelper.buildRoleInfo(request);
        roleRepository.save(roleInfo);
        return new GenericApiResponse<>(CreateRoleResponse.builder()
                .roleId(roleInfo.getId())
                .roleName(roleInfo.getName())
                .permissionsAssigned(roleInfo.getPermissions())
                .errors(errorMap)
                .build());
    }
}
