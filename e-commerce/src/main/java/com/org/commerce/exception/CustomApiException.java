package com.org.commerce.exception;

import org.springframework.http.HttpStatus;


public class CustomApiException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public CustomApiException(HttpStatus status, String message1){
        super(message);
        this.status=status;
        this.message=message1;
    }

    public HttpStatus getHttpStatus(){
        return  status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
