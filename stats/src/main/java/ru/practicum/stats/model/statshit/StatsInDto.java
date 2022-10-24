package ru.practicum.stats.model.statshit;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Dto - информация о записи (входящий запрос)
 */
@Data
@AllArgsConstructor
public class StatsInDto {
    @NotBlank
    private String app;
    @NotBlank
    private String uri;
    @NotBlank
    private String ip;
    @NotBlank
    private String timestamp;
}