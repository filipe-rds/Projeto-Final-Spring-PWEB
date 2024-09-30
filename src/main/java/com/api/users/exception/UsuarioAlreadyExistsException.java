package com.api.users.exception;

public class UsuarioAlreadyExistsException extends RuntimeException {
    public UsuarioAlreadyExistsException(String message) {
        super(message);
    }
}
