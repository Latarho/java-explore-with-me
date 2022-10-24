package ru.practicum.stats.service;

import ru.practicum.stats.model.statshit.StatsInDto;
import ru.practicum.stats.model.statshit.StatsOutDto;

import java.util.List;

public interface StatsService {

    void create(StatsInDto statsInDto);

    List<StatsOutDto> getAll(String start, String  end, List<String> uris, boolean isUnique);
}