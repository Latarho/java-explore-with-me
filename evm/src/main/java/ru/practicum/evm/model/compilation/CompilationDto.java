package ru.practicum.evm.model.compilation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.evm.model.event.EventShortDto;

import java.util.List;

/**
 * DTO - подборка событий
 */
@Data
@RequiredArgsConstructor
public class CompilationDto {
    private Long id;
    private List<EventShortDto> events;
    private boolean pinned;
    private String title;
}