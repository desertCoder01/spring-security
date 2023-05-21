package com.example.demo.controller;


import com.example.demo.model.request.CreatePermissionRequest;
import com.example.demo.model.response.CreatePermissionResponse;
import com.example.demo.model.response.GenericApiResponse;
import com.example.demo.service.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final IPermissionService permissionService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/create")
    ResponseEntity<GenericApiResponse<CreatePermissionResponse>> createPermission(
            @RequestBody List<CreatePermissionRequest> request){
        GenericApiResponse<CreatePermissionResponse> response = permissionService.createPermission(request);
        response.setUri("/permission/create");
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }
}
