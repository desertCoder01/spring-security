package com.example.demo.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class CreatePermissionResponse {
    private Map<String, String> success;
    private Map<String, String> failure;
}
