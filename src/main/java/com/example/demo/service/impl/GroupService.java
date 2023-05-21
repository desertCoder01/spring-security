package com.example.demo.service.impl;


import com.example.demo.model.entity.GroupInfo;
import com.example.demo.model.request.CreateGroupRequest;
import com.example.demo.model.request.CreateGroupResponse;
import com.example.demo.model.response.GenericApiResponse;
import com.example.demo.service.IGroupService;
import com.example.demo.utils.GroupHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupService implements IGroupService {

    private final GroupHelper groupHelper;

    @Override
    public GenericApiResponse<CreateGroupResponse> createGroup(CreateGroupRequest request) {
        groupHelper.validateCreateGroupRequest(request);
        GroupInfo groupInfo = groupHelper.createGroupInfo(request);
        return new GenericApiResponse<>(CreateGroupResponse.builder()
                .groupId(groupInfo.getId())
                .name(groupInfo.getName())
                .description(groupInfo.getDescription())
                .initialMembers(groupInfo.getMembers())
                .superAdmin(groupInfo.getSuperAdmin())
                .memberPostAllowed(groupInfo.isMemberPostAllowed())
                .build());
    }
}
