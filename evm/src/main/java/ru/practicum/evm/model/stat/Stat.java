package ru.practicum.evm.model.stat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stat {
    private String app;
    private String uri;
    private Long hits;
}