package ru.practicum.ewm.web.unauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.compilation.CompilationDto;
import ru.practicum.ewm.service.compilation.CompilationService;

import java.util.List;

/**
 * Класс-контроллер работа с подборками (публичная часть API)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {

    private final CompilationService compilationService;

    /**
     * Получение подборок событий
     * @param pinned искать только закрепленные/не закрепленные подборки
     * @param from количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size количество элементов в наборе
     * @return список подборок событий
     */
    @GetMapping
    public List<CompilationDto> getAllWithPagination(@RequestParam(required = false) Boolean pinned,
                                                     @RequestParam int from, @RequestParam int size) {
        log.info("Получен запрос - получение списка подборок, в списке элементов: " + size);
        return compilationService.getAllWithPagination(pinned, from, size);
    }

    /**
     * Получение подборки событий по его id
     * @param compId id подборки
     * @return объект класса Compilation соответствующий id
     */
    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable Long compId) {
        log.info("Получен запрос - получение информации о подборке: " + compId);
        return compilationService.getById(compId);
    }
}