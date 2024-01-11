package com.gsdc.server.exceptions;

import org.springframework.http.HttpStatus;

public class WrongPasswordException extends AbstractException{
    private  static final String MESSAGE = "Wrong password!";

    public WrongPasswordException() {
        super(HttpStatus.BAD_REQUEST, WrongPasswordException.MESSAGE);
    }
}
