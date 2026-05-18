package com.schefer.agenda.exception;

public class SemPermissaoException extends RuntimeException {
    public SemPermissaoException(String mensagem) {
        super(mensagem);
    }
}