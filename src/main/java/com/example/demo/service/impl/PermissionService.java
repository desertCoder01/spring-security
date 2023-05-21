package com.example.demo.service.impl;

import com.example.demo.model.entity.PermissionInfo;
import com.example.demo.model.repository.PermissionRepository;
import com.example.demo.model.request.CreatePermissionRequest;
import com.example.demo.model.response.CreatePermissionResponse;
import com.example.demo.model.response.GenericApiResponse;
import com.example.demo.service.IPermissionService;
import com.example.demo.utils.AccessHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionService implements IPermissionService {

    private final PermissionRepository permissionRepository;
    private final AccessHelper accessHelper;

    @Override
    public GenericApiResponse<CreatePermissionResponse> createPermission(List<CreatePermissionRequest> request) {
        accessHelper.validateCreatePermissionRequest(request);
        Map<String,String> success = new HashMap<>();
        Map<String,String> failure = new HashMap<>();
        Set<String> permitNames = request.stream().map(req ->
                req.getPermissionName().toUpperCase()).collect(Collectors.toSet());
        List<PermissionInfo> existingPermissions = permissionRepository.findAllByName(permitNames);
        if(!ObjectUtils.isEmpty(existingPermissions)){
            existingPermissions.forEach(permit -> {
                failure.put(permit.getName(),"Permission already exist");
                permitNames.remove(permit.getName());
            });
        }
        for(CreatePermissionRequest permit : request){
            if(permitNames.contains(permit.getPermissionName().toUpperCase())){
                PermissionInfo permission = accessHelper.createPermissionInfo(permit.getPermissionName(),
                        permit.getPermissionDescription());
                success.put(permission.getName(),"Successfully created");
                permissionRepository.save(permission);
            }
        }
        return new GenericApiResponse<>(CreatePermissionResponse.builder()
                .success(success)
                .failure(failure)
                .build());
    }
}
