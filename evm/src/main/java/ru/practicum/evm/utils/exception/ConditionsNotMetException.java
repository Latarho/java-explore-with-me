package ru.practicum.evm.utils.exception;

public class ConditionsNotMetException extends RuntimeException {

    public ConditionsNotMetException(String message) {
        super(message);
    }
}