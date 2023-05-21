package com.example.demo.security;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class SessionUserDetails implements UserDetails {

    private String username;
    private String password;
    private String userId;
    private String role;
    private List<String> permissions;

    @Override
    public Set<Authority> getAuthorities() {
        Set<Authority> authorities = Set.of(new Authority("ROLE_" + role));
        permissions.forEach(permit -> authorities.add(new Authority(permit)));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
