package ru.practicum.stats.model.statshit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto - информация о записи
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsOutDto {
    private String app;
    private String uri;
    private int hits;
}