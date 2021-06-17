package com.everis.client.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ClientExceptionHandler extends RuntimeException {

    public ClientExceptionHandler(Throwable cause) {
        super(cause);
    }
}
