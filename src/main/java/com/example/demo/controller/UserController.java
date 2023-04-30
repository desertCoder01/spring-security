package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/admin-list")
    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasPermission(#sessionData, 'WRITE')")
    ResponseEntity<List<String>> getAdminList() {
        return ResponseEntity.ok(Arrays.asList("Pankaj Kumar", "Shiva Singh", "Abhilasha Singh"));
    }
}
