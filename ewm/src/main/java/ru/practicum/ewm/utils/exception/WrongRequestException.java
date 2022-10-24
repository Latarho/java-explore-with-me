package ru.practicum.ewm.utils.exception;

public class WrongRequestException extends RuntimeException {

    public WrongRequestException(String message) {
        super(message);
    }
}