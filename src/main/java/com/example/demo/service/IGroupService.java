package com.example.demo.service;


import com.example.demo.model.request.CreateGroupRequest;
import com.example.demo.model.request.CreateGroupResponse;
import com.example.demo.model.response.GenericApiResponse;

public interface IGroupService {
    GenericApiResponse<CreateGroupResponse> createGroup(CreateGroupRequest request);
}
