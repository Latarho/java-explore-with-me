package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.model.statshit.StatsHitMapper;
import ru.practicum.stats.model.statshit.StatsInDto;
import ru.practicum.stats.model.statshit.StatsOutDto;
import ru.practicum.stats.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация интерфейса {@link StatsService}
 */
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService{

    private final StatsRepository statsRepository;

    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public void create(StatsInDto statsInDto) {
        statsRepository.save(StatsHitMapper.toStatsHit(statsInDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatsOutDto> getAll(String start, String end, List<String> uris, boolean isUnique) {
        List<StatsOutDto> statsOutDtoList = new ArrayList<>();
        LocalDateTime startTime = LocalDateTime.parse(start, FORMAT);
        LocalDateTime endTime = LocalDateTime.parse(end, FORMAT);
        for (String uri : uris) {
            if (!isUnique && startTime.equals(endTime)) {
                statsOutDtoList.add(StatsHitMapper.toStatsOutDto(
                        statsRepository.findAllByUri(uri)));
            } else if (isUnique && startTime.equals(endTime)) {
                statsOutDtoList.add(StatsHitMapper.toStatsOutDto(
                        statsRepository.findDistinctByUriAndIpAndApp(uri)));
            } else if (!isUnique && !startTime.equals(endTime)) {
                statsOutDtoList.add(StatsHitMapper.toStatsOutDto(
                        statsRepository.findAllByUriAndTimestampBetween(uri, startTime, endTime)));
            } else if (isUnique && !startTime.equals(endTime)) {
                statsOutDtoList.add(StatsHitMapper.toStatsOutDto(
                        statsRepository.findDistinctByUriAndTimestampBetween(uri, startTime, endTime)));
            }
        }
        return statsOutDtoList;
    }
}