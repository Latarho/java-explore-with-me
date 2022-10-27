package ru.practicum.ewm.utils.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.utils.exception.EntityNotFoundException;
import ru.practicum.ewm.model.stat.Stats;
import ru.practicum.ewm.model.stat.StatsDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class StatsClient {

    protected final RestTemplate rest;

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String statServerUrl, RestTemplateBuilder builder) {
        rest = builder.uriTemplateHandler(new DefaultUriBuilderFactory(statServerUrl)).build();
    }

    public void send(StatsDto statsDto) {
        rest.postForEntity("/hit", statsDto, Object.class);
    }

    public Stats getEventStat(Long id, String start, String end) {
        Stats[] result = rest.getForObject("/stats?start=" + URLEncoder.encode(start, StandardCharsets.UTF_8) +
                "&end=" + URLEncoder.encode(end, StandardCharsets.UTF_8) +
                "&uris=" + URLEncoder.encode("/events/" + id, StandardCharsets.UTF_8), Stats[].class);
        if (result == null) {
            throw new EntityNotFoundException("Отсутствует статистика");
        }
        return result[0];
    }
}