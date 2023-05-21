package com.example.demo.model.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateGroupRequest {
    private String name;
    private String description;
    private List<String> members;
    private boolean memberPostAllowed;
}
