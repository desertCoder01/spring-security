package com.example.demo.controller;

import com.example.demo.security.LoginResponse;
import com.example.demo.security.PrincipalUserDetails;
import com.example.demo.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login/admin")
    ResponseEntity<LoginResponse> loginAdmin(){
        PrincipalUserDetails principalUserDetails = new PrincipalUserDetails(
                "pankaj.kumar@gmail.com",
                "ADMIN",
                "Pankaj@123",
                Arrays.asList("READ","WRITE","DELETE")
        );
        String accessToken = tokenProvider.createAccessToken(principalUserDetails);
        String refreshToken = tokenProvider.createRefreshToken(principalUserDetails);
        return ResponseEntity.ok(new LoginResponse(accessToken,refreshToken));
    }

    @PostMapping("/login/customer")
    ResponseEntity<LoginResponse> loginCustomer(){
        PrincipalUserDetails principalUserDetails = new PrincipalUserDetails(
                "vikash.kumar@gmail.com",
                "CUSTOMER",
                "Vikash@123",
                Arrays.asList("READ")
        );
        String accessToken = tokenProvider.createAccessToken(principalUserDetails);
        String refreshToken = tokenProvider.createRefreshToken(principalUserDetails);
        return ResponseEntity.ok(new LoginResponse(accessToken,refreshToken));
    }
}

