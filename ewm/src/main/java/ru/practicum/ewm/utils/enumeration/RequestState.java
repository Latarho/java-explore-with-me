package ru.practicum.ewm.utils.enumeration;

/**
 * Список состояний жизненного цикла запроса
 */
public enum RequestState {
    PENDING,
    REJECTED,
    CONFIRMED,
    CANCELED
}