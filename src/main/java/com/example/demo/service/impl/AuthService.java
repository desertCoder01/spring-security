package com.example.demo.service.impl;


import com.example.demo.exception.InvalidRequestException;
import com.example.demo.model.constant.AppConstants;
import com.example.demo.model.entity.UserInfo;
import com.example.demo.model.repository.UserRepository;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.SignupRequest;
import com.example.demo.model.response.ApiResponse;
import com.example.demo.model.response.GenericApiResponse;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.model.response.SignupResponse;
import com.example.demo.security.SessionUserDetails;
import com.example.demo.security.TokenProvider;
import com.example.demo.service.IAuthService;
import com.example.demo.utils.AuthHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService implements IAuthService {

    private final AuthHelper authHelper;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Override
    public GenericApiResponse<SignupResponse> signup(SignupRequest request) {
        log.info("Received signup request for :" + request);
        UserInfo userInfo = authHelper.validateSignupRequest(request);
        try {
            String link = authHelper.sendActivationLink(userInfo);
            log.info("Account activation link is :" + link);
            userRepository.save(userInfo);
            SignupResponse response = authHelper.buildSignupResponse(userInfo.getId(), link);
            return new GenericApiResponse<>(response);
        } catch (Exception ex) {
            log.info(AppConstants.ERROR_MESSAGE + ex.getMessage());
            return new GenericApiResponse<>(AppConstants.ERROR_MESSAGE, ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GenericApiResponse<ApiResponse> confirmAccount(String id, long field) {
        log.info("Received account activation request for :" + id);
        Optional<UserInfo> optionalUserInfo = userRepository.findById(id);
        if (optionalUserInfo.isEmpty()) {
            throw new InvalidRequestException("Invalid link : No details found");
        }
        UserInfo userInfo = optionalUserInfo.get();
        if (userInfo.isActive()) {
            throw new InvalidRequestException("Account already activated");
        }
        long currentTimeStamp = LocalDateTime.now(ZoneOffset.UTC)
                .toInstant(ZoneOffset.UTC).toEpochMilli();
        if ((field + 3600000) < currentTimeStamp) {
            throw new InvalidRequestException("Link has expired");
        }
        try {
            userInfo.setActive(true);
            userRepository.save(userInfo);
            return new GenericApiResponse<>(new ApiResponse("Account activated for :" + id, true));
        } catch (Exception ex) {
            log.info(AppConstants.ERROR_MESSAGE + ex.getMessage());
            return new GenericApiResponse<>(AppConstants.ERROR_MESSAGE, ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GenericApiResponse<LoginResponse> login(LoginRequest request) {
        log.info("Received login request for :" + request);
        authHelper.validateLoginRequest(request);
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(), request.getPassword()));
            SessionUserDetails sessionUserDetails = (SessionUserDetails) authentication.getPrincipal();
            String accessToken = tokenProvider.createAccessToken(sessionUserDetails);
            String refreshToken = tokenProvider.createRefreshToken(sessionUserDetails);
            LoginResponse response = LoginResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
            return new GenericApiResponse<>(response);
        } catch (AuthenticationException ex) {
            log.info(AppConstants.ERROR_MESSAGE + ex.getMessage());
            return new GenericApiResponse<>(AppConstants.ERROR_MESSAGE, ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.info(AppConstants.ERROR_MESSAGE + ex.getMessage());
            return new GenericApiResponse<>(AppConstants.ERROR_MESSAGE, ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
