package ru.practicum.ewm.model.stat;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Dto - статистика
 */
@Data
@AllArgsConstructor
public class StatsDto {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}