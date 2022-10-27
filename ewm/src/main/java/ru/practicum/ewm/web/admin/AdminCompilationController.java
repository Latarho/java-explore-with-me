package ru.practicum.ewm.web.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.compilation.CompilationDto;
import ru.practicum.ewm.model.compilation.NewCompilationDto;
import ru.practicum.ewm.service.compilation.CompilationService;

import javax.validation.Valid;

/**
 * Класс-контроллер работа с подборками (административная часть API)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminCompilationController {

    private final CompilationService compilationService;

    /**
     * Добавление новой подборки
     * @param newCompilationDto данные добавляемой подборки
     * @return объект класса Compilation (новая подборка)
     */
    @PostMapping("/compilations")
    public CompilationDto create(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Получен запрос - добавление новой подборки: " + newCompilationDto.toString());
        return compilationService.create(newCompilationDto);
    }

    @DeleteMapping("/compilations/{compId}")
    public void delete(@PathVariable Long compId) {
        log.info("Получен запрос - удаление подборки: " + compId);
        compilationService.delete(compId);
    }

    @PatchMapping("/compilations/{compId}/pin")
    public void pin(@PathVariable Long compId) {
        log.info("Получен запрос - закрепление подборки на главной странице: " + compId);
        compilationService.pin(compId);
    }

    @DeleteMapping("/compilations/{compId}/pin")
    public void unpin(@PathVariable Long compId) {
        log.info("Получен запрос - открепление подборки на главной странице: " + compId);
        compilationService.unpin(compId);
    }

    @PatchMapping("/compilations/{compId}/events/{eventId}")
    public void addEvent(@PathVariable Long compId, @PathVariable Long eventId) {
        log.info("Получен запрос - добавление события в подборку: " + compId + " событие: " + eventId);
        compilationService.addEvent(compId, eventId);
    }

    @DeleteMapping("/compilations/{compId}/events/{eventId}")
    public void removeEvent(@PathVariable Long compId, @PathVariable Long eventId) {
        log.info("Получен запрос - удаление события из подборки: " + compId + " событие: " + eventId);
        compilationService.removeEvent(compId, eventId);
    }
}