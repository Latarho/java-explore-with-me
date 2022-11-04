package ru.practicum.stats.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.model.statshit.StatsInDto;
import ru.practicum.stats.model.statshit.StatsOutDto;
import ru.practicum.stats.service.StatsService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    /**
     * Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем
     * @param statsInDto данные добавляемой информации (название сервиса, uri и ip пользователя)
     */
    @PostMapping("/hit")
    public void create(@RequestBody StatsInDto statsInDto) {
        log.info("Получен запрос - на uri сервиса был отправлен запрос: " + statsInDto.toString());
        statsService.create(statsInDto);
    }

    /**
     * Получение статистики по посещениям
     * @param start дата и время начала диапазона за который нужно выгрузить статистику (в формате "yyyy-MM-dd HH:mm:ss")
     * @param end дата и время конца диапазона за который нужно выгрузить статистику (в формате "yyyy-MM-dd HH:mm:ss")
     * @param uris список uri для которых нужно выгрузить статистику
     * @param unique нужно ли учитывать только уникальные посещения (только с уникальным ip)
     * @return статистика по посещениям
     */
    @GetMapping("/stats")
    public List<StatsOutDto> getAll(@RequestParam String start,
                                    @RequestParam String end,
                                    @RequestParam List<String> uris,
                                    @RequestParam(defaultValue = "false") boolean unique) {
        uris.replaceAll(s -> URLDecoder.decode(s, StandardCharsets.UTF_8));
        log.info("Получен запрос - статистика по посещениям за период с " + start + " по " + end + " для URI: " + uris);
        return statsService.getAll(URLDecoder.decode(start, StandardCharsets.UTF_8),
                                   URLDecoder.decode(end, StandardCharsets.UTF_8), uris, unique);
    }
}