package ru.practicum.ewm.utils.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static ru.practicum.ewm.utils.constants.DateTimeConfig.FORMAT;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError onEntityNotFoundException(final EntityNotFoundException exception) {
        log.error(exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().format(FORMAT)
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
                LocalDateTime.now().format(FORMAT)
        );
    }

    @ExceptionHandler(WrongRequestException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError onWrongRequestException(WrongRequestException exception) {
        log.error(exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                HttpStatus.FORBIDDEN.toString(),
                LocalDateTime.now().format(FORMAT)
        );
    }
}