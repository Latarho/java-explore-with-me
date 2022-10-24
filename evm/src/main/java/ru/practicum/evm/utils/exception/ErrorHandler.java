package ru.practicum.evm.utils.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static ru.practicum.evm.utils.constants.DateTimeConfig.FORMAT;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError onUserNotFoundException(final UserNotFoundException exception) {
        log.error(exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().format(FORMAT)
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError onCompilationNotFoundException(final CompilationNotFoundException exception) {
        log.error(exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().format(FORMAT)
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError onStatsNotFoundException(final StatsNotFoundException exception) {
        log.error(exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().format(FORMAT)
        );
    }

    @ExceptionHandler(ConditionsNotMetException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError onConditionsNotMetException(ConditionsNotMetException exception) {
        log.error(exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                HttpStatus.FORBIDDEN.toString(),
                LocalDateTime.now().format(FORMAT)
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError onEventNotFoundException(final EventNotFoundException exception) {
        log.error(exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().format(FORMAT)
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError onRequestNotFoundException(final RequestNotFoundException exception) {
        log.error(exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().format(FORMAT)
        );
    }
}