package com.droux.jtshirt.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TshirtNotFoundException extends RuntimeException {
    public TshirtNotFoundException(String message) {
        super(message);
    }
}
