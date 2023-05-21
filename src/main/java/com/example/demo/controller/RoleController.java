package com.example.demo.controller;

import com.example.demo.model.request.CreateRoleRequest;
import com.example.demo.model.response.CreateRoleResponse;
import com.example.demo.model.response.GenericApiResponse;
import com.example.demo.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/create")
    ResponseEntity<GenericApiResponse<CreateRoleResponse>> createRoleResponse(@RequestBody CreateRoleRequest request){
        GenericApiResponse<CreateRoleResponse> response = roleService.createRole(request);
        response.setUri("/role/create");
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }
}
