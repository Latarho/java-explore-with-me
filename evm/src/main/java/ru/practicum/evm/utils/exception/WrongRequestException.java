package ru.practicum.evm.utils.exception;

public class WrongRequestException extends RuntimeException {

    public WrongRequestException(String message) {
        super(message);
    }
}