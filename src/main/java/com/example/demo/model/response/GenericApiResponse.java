package com.example.demo.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class GenericApiResponse<T> {
    private String uri;
    private Integer httpStatusCode;
    private T data;
    private String errorMessage;
    private String errorDetail;

    public GenericApiResponse(String errorMessage, String otherErrorDetails, HttpStatus status) {
        this.errorMessage = errorMessage;
        this.errorDetail = otherErrorDetails;
        this.httpStatusCode = status.value();
        this.data = null;
    }

    public GenericApiResponse(T data) {
        this.data = data;
        this.httpStatusCode = HttpStatus.OK.value();
        this.data = data;
        this.errorMessage = null;
        this.errorDetail = null;
        this.uri = null;
    }

    public GenericApiResponse(String errorMessage , Boolean isError){
        this.errorMessage = errorMessage;
        this.httpStatusCode = HttpStatus.BAD_REQUEST.value();
        this.errorDetail = null;
        this.data = null;
    }
}
