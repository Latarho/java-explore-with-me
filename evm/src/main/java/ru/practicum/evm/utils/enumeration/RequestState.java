package ru.practicum.evm.utils.enumeration;

/**
 * Список состояний жизненного цикла запроса
 */
public enum RequestState {
    PENDING,
    REJECTED,
    CONFIRMED,
    CANCELED
}