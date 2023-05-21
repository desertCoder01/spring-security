package com.example.demo.controller;


import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.SignupRequest;
import com.example.demo.model.response.ApiResponse;
import com.example.demo.model.response.GenericApiResponse;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.model.response.SignupResponse;
import com.example.demo.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/signup")
    ResponseEntity<GenericApiResponse<SignupResponse>> signup(@RequestBody SignupRequest request){
        GenericApiResponse<SignupResponse> response = authService.signup(request);
        response.setUri("/auth/signup");
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }

    @GetMapping("/confirm-account")
    ResponseEntity<GenericApiResponse<ApiResponse>> confirmAccount(@RequestParam("id") String id,
                                                                   @RequestParam("field") long field){
        GenericApiResponse<ApiResponse> response = authService.confirmAccount(id,field);
        response.setUri("/auth/confirm-account");
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }

    @PostMapping("/login")
    ResponseEntity<GenericApiResponse<LoginResponse>> login(@RequestBody LoginRequest request){
        GenericApiResponse<LoginResponse> response = authService.login(request);
        response.setUri("/auth/login");
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }

}
