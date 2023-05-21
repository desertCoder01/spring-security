package com.example.demo.service;


import com.example.demo.model.request.CreatePermissionRequest;
import com.example.demo.model.response.CreatePermissionResponse;
import com.example.demo.model.response.GenericApiResponse;

import java.util.List;

public interface IPermissionService {
    GenericApiResponse<CreatePermissionResponse> createPermission(List<CreatePermissionRequest> request);
}
