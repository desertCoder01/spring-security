package com.example.demo.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PrincipalUserDetails implements UserDetails {
    private String username;
    private String password;
    private String role;
    private List<String> permissions;

    public PrincipalUserDetails(String email, String role, String password, List<String> permissions) {
        this.role = role;
        this.permissions = permissions;
        this.username = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> set = new HashSet<>();
        set.add(new Authority("ROLE_"+role));
        set.addAll(permissions.stream()
                .map(permit -> new Authority(permit))
                .collect(Collectors.toSet()));
        return set;
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
