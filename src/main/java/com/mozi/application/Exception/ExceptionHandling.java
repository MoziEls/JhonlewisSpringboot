package com.mozi.application.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(value=ProductException.class)
    public ResponseEntity<Object> exception(ProductException exception) {
        return new ResponseEntity<>("Invalid parameter ! please enter valid parameters", HttpStatus.NOT_FOUND);
    }
}
