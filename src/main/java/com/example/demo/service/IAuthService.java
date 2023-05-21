package com.example.demo.service;

import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.SignupRequest;
import com.example.demo.model.response.ApiResponse;
import com.example.demo.model.response.GenericApiResponse;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.model.response.SignupResponse;

public interface IAuthService {

    GenericApiResponse<SignupResponse> signup(SignupRequest request);
    GenericApiResponse<ApiResponse> confirmAccount(String id, long field);
    GenericApiResponse<LoginResponse> login(LoginRequest request);
}
