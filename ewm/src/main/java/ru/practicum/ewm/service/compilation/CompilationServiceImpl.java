package ru.practicum.ewm.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.utils.client.StatsClient;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.model.compilation.CompilationDto;
import ru.practicum.ewm.model.compilation.CompilationMapper;
import ru.practicum.ewm.model.compilation.NewCompilationDto;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.stat.Stats;
import ru.practicum.ewm.repository.CompilationRepository;
import ru.practicum.ewm.service.event.EventService;
import ru.practicum.ewm.utils.exception.EntityNotFoundException;
import ru.practicum.ewm.utils.helpers.PageBuilder;

import java.time.LocalDateTime;
import java.util.*;

import static ru.practicum.ewm.utils.constants.DateTimeConfig.FORMAT;

/**
 * Реализация интерфейса {@link CompilationService}
 */
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventService eventService;
    private final StatsClient statsClient;

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.toCompilationFromNewCompilationDto(newCompilationDto);
        Set<Event> eventSet = new HashSet<>(eventService.getAllById(newCompilationDto.getEvents()));
        compilation.setEvents(eventSet);
        compilationRepository.save(compilation);
        Map<Long, Stats> statHashMap = groupStatEntryListById(getStatsForEventList(compilation.getEvents()));
        return CompilationMapper.toCompilationDto(compilation, statHashMap);
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getById(Long compilationId) {
        Compilation compilation = getCompilationOrThrow(compilationId);
        Map<Long, Stats> statsEntryHashMap = groupStatEntryListById(getStatsForEventList(compilation.getEvents()));
        return CompilationMapper.toCompilationDto(getCompilationOrThrow(compilationId), statsEntryHashMap);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getAllWithPagination(Boolean pinned, int from, int size) {
        Page<Compilation> compilationPage = compilationRepository.findAll(PageBuilder.build(from, size));
        List<Compilation> compilationList = compilationPage.getContent();
        List<CompilationDto> compilationDtos = new ArrayList<>();
        for (Compilation compilation : compilationList) {
            compilationDtos.add(CompilationMapper.toCompilationDto(compilation,
                            groupStatEntryListById(getStatsForEventList(compilation.getEvents()))));
        }
        return compilationDtos;
    }

    @Override
    @Transactional
    public void delete(Long compilationId) {
        getCompilationOrThrow(compilationId);
        compilationRepository.deleteById(compilationId);
    }

    @Override
    @Transactional
    public void pin(Long compilationId) {
        Compilation compilation = getCompilationOrThrow(compilationId);
        compilation.setPinned(true);
    }

    @Override
    @Transactional
    public void unpin(Long compilationId) {
        Compilation compilation = getCompilationOrThrow(compilationId);
        compilation.setPinned(false);
    }

    @Override
    @Transactional
    public void addEvent(Long compilationId, Long eventId) {
        Compilation compilation = getCompilationOrThrow(compilationId);
        Event event = getEventOrThrow(eventId);
        Set<Event> eventList = compilation.getEvents();
        eventList.add(event);
    }

    @Override
    @Transactional
    public void removeEvent(Long compilationId, Long eventId) {
        Compilation compilation = getCompilationOrThrow(compilationId);
        Event event = getEventOrThrow(eventId);
        Set<Event> eventList = compilation.getEvents();
        eventList.remove(event);
    }

    private Compilation getCompilationOrThrow(Long compilationId) {
        return compilationRepository.findById(compilationId).orElseThrow(() ->
                new EntityNotFoundException("Отсутствует подборка событий id: " + compilationId));
    }

    private Event getEventOrThrow(Long eventId) {
        return eventService.getEventOrThrow(eventId);
    }

    private Map<Long, Stats> groupStatEntryListById(List<Stats> statsList) {
        Map<Long, Stats> statHashMap = new HashMap<>();
        if (statsList.isEmpty() || statsList.get(0).getApp() == null) {
            return statHashMap;
        } else {
            for (Stats stats : statsList) {
                String entryUri = stats.getUri();
                entryUri = entryUri.substring(entryUri.lastIndexOf("/") + 1);
                statHashMap.put(Long.parseLong(entryUri), stats);
            }
        }
        return statHashMap;
    }

    private List<Stats> getStatsForEventList(Set<Event> eventList) {
        List<Stats> statsEntryList = new ArrayList<>();
        for (Event event : eventList) {
            statsEntryList.add(statsClient.getEventStat(event.getId(),
                    LocalDateTime.now().format(FORMAT),
                    LocalDateTime.now().format(FORMAT)));
        }
        return statsEntryList;
    }
}