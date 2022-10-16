package ru.practicum.evm.exception;

public class CategoryHaveEventsException extends RuntimeException {

    public CategoryHaveEventsException(String message) {
        super(message);
    }
}