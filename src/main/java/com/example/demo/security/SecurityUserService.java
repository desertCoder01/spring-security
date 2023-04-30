package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class SecurityUserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user detail for :"+username);
        switch (username){
            case "pankaj.kumar@gmail.com":
                return UserInfo.builder()
                        .username(username)
                        .password("Pankaj@123")
                        .permissions(Arrays.asList("READ","WRITE","DELETE"))
                        .role("ADMIN")
                        .build();
            case "vikash.kumar@gmail.com":
                return UserInfo.builder()
                        .username(username)
                        .role("CUSTOMER")
                        .permissions(Arrays.asList("READ"))
                        .password("Vikash@123")
                        .build();
            default:
                throw new UsernameNotFoundException("User not found");
        }

    }
}
