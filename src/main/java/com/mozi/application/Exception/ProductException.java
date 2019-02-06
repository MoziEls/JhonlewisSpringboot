package com.mozi.application.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND,reason="This is not valid parameter")
public class ProductException extends RuntimeException{

    private static final long serialVersionUID = 100L;
}