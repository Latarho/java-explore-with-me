package ru.practicum.evm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String reason;
    private String status;
    private String timestamp;
}