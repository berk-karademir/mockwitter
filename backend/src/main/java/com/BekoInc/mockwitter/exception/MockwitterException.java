package com.BekoInc.mockwitter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class MockwitterException extends RuntimeException {

    private HttpStatus httpStatus;

    public MockwitterException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }


}
