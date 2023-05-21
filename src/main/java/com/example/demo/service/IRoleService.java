package com.example.demo.service;

import com.example.demo.model.request.CreateRoleRequest;
import com.example.demo.model.response.CreateRoleResponse;
import com.example.demo.model.response.GenericApiResponse;

public interface IRoleService {
    GenericApiResponse<CreateRoleResponse> createRole(CreateRoleRequest request);
}
