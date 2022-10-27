package ru.practicum.ewm.model.compilation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.EventMapper;
import ru.practicum.ewm.model.event.EventShortDto;
import ru.practicum.ewm.model.stat.Stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Маппер объекта класса Compilation {@link ru.practicum.ewm.model.compilation.Compilation}
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation compilation, Map<Long, Stats> statHashMap) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setPinned(compilation.isPinned());
        compilationDto.setTitle(compilation.getTitle());
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        Set<Event> eventList = compilation.getEvents();
        for (Event event : eventList) {
            eventShortDtoList.add(EventMapper.toEventShortDto(event, statHashMap.get(event.getId())));
        }
        compilationDto.setEvents(eventShortDtoList);
        return compilationDto;
    }

    public static Compilation toCompilationFromNewCompilationDto(NewCompilationDto newCompilationDto) {
        Compilation compilation = new Compilation();
        compilation.setTitle(newCompilationDto.getTitle());
        compilation.setPinned(newCompilationDto.isPinned());
        return compilation;
    }
}