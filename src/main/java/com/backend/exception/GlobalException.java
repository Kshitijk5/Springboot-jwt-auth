package com.backend.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(err->{
            String fieldName =((FieldError) err).getField();
            String error = err.getDefaultMessage();
            errors.put(fieldName,error);
        });

        return  new ResponseEntity<Object>(errors, HttpStatusCode.valueOf(status.value()));
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorResponse> APIExceptionHandler(APIException apiException){
        ErrorResponse response = new ErrorResponse();
        response.setMessage(apiException.getMessage());
        response.setStatusCode(apiException.getStatus().value());
        return new ResponseEntity<>(response,apiException.getStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception ex){


        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
