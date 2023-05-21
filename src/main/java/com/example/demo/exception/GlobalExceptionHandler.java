package com.example.demo.exception;

import com.example.demo.model.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse response = new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse> InvalidRequestExceptionHandler(InvalidRequestException ex){
        String message = ex.getMessage();
        ApiResponse response = new ApiResponse(message,false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ApiResponse> customRuntimeException(CustomRuntimeException ex){
        String message = ex.getMessage();
        ApiResponse response = new ApiResponse(message,false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity<ApiResponse> customUnauthorizedException(CustomUnauthorizedException ex){
        String message = ex.getMessage();
        ApiResponse response = new ApiResponse(message,false);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
