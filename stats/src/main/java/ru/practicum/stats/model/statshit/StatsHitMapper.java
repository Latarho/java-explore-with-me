package ru.practicum.stats.model.statshit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.stats.model.app.App;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Маппер объекта класса StatsHit {@link StatsHit}
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatsHitMapper {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static StatsHit toStatsHit(StatsInDto statsInDto) {
        StatsHit statsHit = new StatsHit();
        App app = new App();
        app.setAppName(statsInDto.getApp());
        statsHit.setApp(app);
        statsHit.setIp(statsInDto.getIp());
        statsHit.setUri(statsInDto.getUri());
        statsHit.setTimestamp(LocalDateTime.parse(statsInDto.getTimestamp(), format));
        return statsHit;
    }

    public static StatsOutDto toStatsOutDto(List<StatsHit> statsHit) {
        if (statsHit.isEmpty()) {
            return new StatsOutDto();
        }
        return new StatsOutDto(
                statsHit.get(0).getApp().getAppName(),
                statsHit.get(0).getUri(),
                statsHit.size()
        );
    }
}