package com.example.demo.controller;


import com.example.demo.model.request.CreateGroupRequest;
import com.example.demo.model.request.CreateGroupResponse;
import com.example.demo.model.response.GenericApiResponse;
import com.example.demo.service.IGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupController {

    private final IGroupService groupService;

    @Secured("ROLE_SUPER_ADMIN")
    @PostMapping("/create")
    ResponseEntity<GenericApiResponse<CreateGroupResponse>> createGroup(@RequestBody CreateGroupRequest request){
        GenericApiResponse<CreateGroupResponse> response = groupService.createGroup(request);
        response.setUri("/group/create");
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }
}
