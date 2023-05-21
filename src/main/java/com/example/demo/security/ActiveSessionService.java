package com.example.demo.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class ActiveSessionService {

    private static final Supplier<AccessDeniedException> accessDenied = () -> new AccessDeniedException("User unauthorized");

    private Optional<SessionUserDetails> optionalSessionData() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication()).map(
                authentication -> (SessionUserDetails) authentication.getPrincipal()
        );
    }

    public SessionUserDetails getSessionData() {
        return optionalSessionData().orElseThrow(accessDenied);
    }

    public String getCurrentUsername() {
        return getSessionData().getUsername();
    }

    public String getCurrentUserRole() {
        return getSessionData().getRole();
    }

    public List<String> getCurrentUserPermissions() {
        return getSessionData().getPermissions();
    }

    public String getCurrentUserId() {
        return getSessionData().getUserId();
    }

}