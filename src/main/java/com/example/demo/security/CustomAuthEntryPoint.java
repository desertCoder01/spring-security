package com.example.demo.security;

import com.example.demo.model.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;


@Component
public class CustomAuthEntryPoint extends BasicAuthenticationEntryPoint {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=" +getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ApiResponse apiResponse = new ApiResponse();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        apiResponse.setMessage("UNAUTHORIZED_ACCESS : " +(!ObjectUtils.isEmpty(request.getAttribute("error_message")) ?
                request.getAttribute("error_message") :"Invalid token") );
        apiResponse.setSuccess(false);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.getWriter().flush();
    }


    @Override
    public void afterPropertiesSet() {
        setRealmName("DeveloperStack");
        super.afterPropertiesSet();
    }
}

