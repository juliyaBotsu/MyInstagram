package com.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserExistException extends RuntimeException {
    public UserExistException(String the_user_has_already_exist) {
        super(the_user_has_already_exist);
    }
}
