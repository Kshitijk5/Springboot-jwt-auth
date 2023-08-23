package com.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class APIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public APIException(String message) {
        super(message);
    }

    public APIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
