package com.gsdc.server.exceptions;

import org.springframework.http.HttpStatus;

public class AccountAlreadyExistsException extends AbstractException{

    private  static final String MESSAGE = "Account already exist!";
    private  static final String MESSAGE_EMAIL = "Account with email %S, or username %S already exist!";
    private  static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public AccountAlreadyExistsException() {
        super(AccountAlreadyExistsException.STATUS, AccountAlreadyExistsException.MESSAGE);
    }

    public AccountAlreadyExistsException(String email, String username) {
        super(AccountAlreadyExistsException.STATUS, String.format(AccountAlreadyExistsException.MESSAGE_EMAIL, email, username));
    }

}
