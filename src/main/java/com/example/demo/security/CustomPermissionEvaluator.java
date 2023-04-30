package com.example.demo.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object targetDomainObject, Object permission) {
        System.out.println("Permission first method is called");
//        if (authentication == null || !authentication.isAuthenticated() || !(permission instanceof String)) {
//            return false;
//        }
        UserInfo userInfo = (UserInfo)authentication.getPrincipal();
        List<String> permissions = new ArrayList<>();
        for (GrantedAuthority authority : userInfo.getAuthorities()) {
            permissions.add(authority.getAuthority());
        }
        return permissions.contains(permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId,
                                 String targetType, Object permission) {
        System.out.println("Permission second method is called");
        return false;
    }
}
