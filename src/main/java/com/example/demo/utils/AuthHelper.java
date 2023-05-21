package com.example.demo.utils;

import com.example.demo.exception.InvalidRequestException;
import com.example.demo.model.constant.AppConstants;
import com.example.demo.model.entity.RoleInfo;
import com.example.demo.model.entity.UserInfo;
import com.example.demo.model.repository.RoleRepository;
import com.example.demo.model.repository.UserRepository;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.SignupRequest;
import com.example.demo.model.response.SignupResponse;
import com.example.demo.security.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthHelper {

    private final CustomPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserInfo validateSignupRequest(SignupRequest request) {
        if(ObjectUtils.isEmpty(request))
            throw new InvalidRequestException("Invalid Request : Empty request");
        if (!StringUtils.hasText(request.getEmail()) || !isValidEmail(request.getEmail()))
            throw new InvalidRequestException("Invalid request : Invalid email provided");
        if(!StringUtils.hasText(request.getPassword()) || !isValidPassword(request.getPassword()))
            throw new InvalidRequestException("Invalid request : Weak password");
        if(!StringUtils.hasText(request.getName()))
            throw new InvalidRequestException("Invalid request : Empty name provided");
        UserInfo userInfo = userRepository.findByEmail(request.getEmail());
        if(!ObjectUtils.isEmpty(userInfo)){
            if(userInfo.isActive()){
                throw new InvalidRequestException("Account already exist " +
                        "with this email :"+request.getEmail());
            }
            userInfo.setPassword(passwordEncoder.encode(request.getPassword()));
            return userInfo;
        }
        RoleInfo roleInfo = roleRepository.findByName("CUSTOMER");
        return UserInfo.builder()
                .id(UUID.randomUUID().toString().replace("-",""))
                .role(roleInfo.getId())
                .active(false)
                .name(request.getName())
                .email(request.getEmail().toLowerCase())
                .permissions(new ArrayList<>())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(AppConstants.CURRENT_TIME)
                .build();
    }

    public Boolean isValidEmail(String email) {
        return !ObjectUtils.isEmpty(email)
                && email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
                /*"^[a-zA-Z0-9_+&*-]+(?:\\." +
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$"*/);
    }

    public Boolean isValidPassword(String password) {
        return !ObjectUtils.isEmpty(password)
                && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,25}$");
    }

    public String sendActivationLink(UserInfo userInfo) {
        log.info("sending account activation link for :"+userInfo);
        String baseUrl = "http://localhost:8081/auth";
        long currentTimeStamp = LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli();
        return baseUrl +"/confirm-account?id="+userInfo.getId()+"&field="+currentTimeStamp;
    }

    public SignupResponse buildSignupResponse(String id, String link) {
        return SignupResponse.builder()
                .userId(id)
                .link(link)
                .message("Please click on the above link to activate the account")
                .build();
    }

    public void validateLoginRequest(LoginRequest request) {
        if(ObjectUtils.isEmpty(request))
            throw new InvalidRequestException("Invalid Request : Empty request");
        if(StringUtils.hasText(request.getEmail()) && isValidEmail(request.getEmail()) &&
                StringUtils.hasText(request.getPassword()) && isValidPassword(request.getPassword())){
            log.info("Validation completed for login request :"+request);
        }else
            throw new InvalidRequestException("Invalid request : Invalid details provided");
    }
}
