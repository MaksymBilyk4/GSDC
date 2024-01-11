package com.gsdc.server.exceptions;

import org.springframework.http.HttpStatus;

public class CouldNotFindAccountException extends AbstractException{

    private  static final String MESSAGE = "Sorry, we could not find your account!";
    private  static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public CouldNotFindAccountException() {
        super(CouldNotFindAccountException.STATUS, CouldNotFindAccountException.MESSAGE);
    }

    public CouldNotFindAccountException(Boolean show) {
        super(CouldNotFindAccountException.STATUS, CouldNotFindAccountException.MESSAGE, show);
    }
}
