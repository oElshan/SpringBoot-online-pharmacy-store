package ru.isha.store.rest.exception;

import org.springframework.http.HttpStatus;

public class ErrorRestDTO {
    private final String message;
    private final HttpStatus httpStatus;

    public ErrorRestDTO(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }


    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
