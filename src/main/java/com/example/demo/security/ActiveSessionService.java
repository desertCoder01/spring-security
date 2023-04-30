package com.example.demo.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class ActiveSessionService {

    private static Supplier<AccessDeniedException> accessDenied= () -> new AccessDeniedException("User unauthorized");


    private Optional<UserInfo> optionalSessionData(){
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication()).map(
                authentication -> (UserInfo) authentication.getPrincipal()
        );
    }

    public UserInfo getSessionData()  {
        return optionalSessionData().orElseThrow(accessDenied);
    }

}