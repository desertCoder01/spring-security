package com.example.demo.utils;

import com.example.demo.model.constant.AppConstants;
import com.example.demo.model.entity.GroupInfo;
import com.example.demo.model.request.CreateGroupRequest;
import com.example.demo.security.ActiveSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupHelper {

    private final ActiveSessionService sessionService;


    public void validateCreateGroupRequest(CreateGroupRequest request) {

    }

    public GroupInfo createGroupInfo(CreateGroupRequest request) {
        String superAdminId = sessionService.getCurrentUserId();
        return GroupInfo.builder()
                .createdAt(AppConstants.CURRENT_TIME)
                .id(UUID.randomUUID().toString().replace("-",""))
                .members(request.getMembers())
                .admins(Arrays.asList(superAdminId))
                .superAdmin(superAdminId)
                .memberPostAllowed(request.isMemberPostAllowed())
                .name(request.getName())
                .build();
    }
}
