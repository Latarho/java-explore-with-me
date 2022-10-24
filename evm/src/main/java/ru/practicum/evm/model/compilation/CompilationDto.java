package ru.practicum.evm.model.compilation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.evm.model.event.EventShortDto;

import java.util.List;

/**
 * DTO - подборка событий
 */
@Getter
@Setter
@RequiredArgsConstructor
public class CompilationDto {
    private Long id; // id подборки
    private List<EventShortDto> events; // список событий входящих в подборку
    private boolean pinned; // закреплена ли подборка на главной странице сайта
    private String title; // заголовок подборки
}