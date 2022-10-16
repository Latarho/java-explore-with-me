package ru.practicum.evm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError onUserNotFoundException(final UserNotFoundException exception) {
        log.error(exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().format(format)
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError onCategoryNotFoundException(final CategoryNotFoundException exception) {
        log.error(exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().format(format)
        );
    }

    @ExceptionHandler(CategoryHaveEventsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError onConditionsNotMetException(CategoryHaveEventsException exception) {
        log.error(exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                HttpStatus.FORBIDDEN.toString(),
                LocalDateTime.now().format(format)
        );
    }
}