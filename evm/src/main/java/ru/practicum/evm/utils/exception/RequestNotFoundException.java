package ru.practicum.evm.utils.exception;

public class RequestNotFoundException extends RuntimeException{

    public RequestNotFoundException(String message) {
        super(message);
    }
}