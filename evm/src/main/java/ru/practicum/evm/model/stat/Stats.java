package ru.practicum.evm.model.stat;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Модель - статистика
 */
@Data
@AllArgsConstructor
public class Stats {
    private String app;
    private String uri;
    private Long hits;
}