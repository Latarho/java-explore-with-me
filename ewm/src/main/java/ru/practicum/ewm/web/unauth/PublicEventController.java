package ru.practicum.ewm.web.unauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.utils.enumeration.EventValues;
import ru.practicum.ewm.model.event.EventFullDto;
import ru.practicum.ewm.model.event.EventShortDto;
import ru.practicum.ewm.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Arrays;
import java.util.List;

/**
 * Класс-контроллер работа с событиями (публичная часть API)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {

    private final EventService eventService;

    /**
     * Получение событий с возможностью фильтрации
     * @param text текст для поиска в содержимом аннотации и подробном описании события
     * @param categories список идентификаторов категорий в которых будет вестись поиск
     * @param isPaid поиск только платных/бесплатных событий
     * @param rangeStart дата и время не раньше которых должно произойти событие
     * @param rangeEnd дата и время не позже которых должно произойти событие
     * @param onlyAvailable - только события у которых не исчерпан лимит запросов на участие
     * @param sort вариант сортировки: по дате события или по количеству просмотров
     * @param from количество событий, которые нужно пропустить для формирования текущего набора
     * @param size количество событий в наборе
     * @return информация обо всех событиях подходящих под переданные условия
     */
    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(required = false) String text,
                                         @RequestParam(required = false) List<Long> categories,
                                         @RequestParam(defaultValue = "true") Boolean isPaid,
                                         @RequestParam(required = false) String rangeStart,
                                         @RequestParam(required = false) String rangeEnd,
                                         @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                         @RequestParam(required = false) EventValues sort,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                         @Positive @RequestParam(defaultValue = "10") int size,
                                         HttpServletRequest request) {
        log.info("Получен запрос - поиск событий по параметрам: text: " + text + " isPaid: " + isPaid +
                 " categories: " + Arrays.toString(categories.toArray()) + " rangeStart: " + rangeStart +
                 " rangeEnd: " + rangeEnd + " доступно участие: " + onlyAvailable + " пропущено событий: " + from +
                 " количество событий в наборе: " + size);
        return eventService.getEvents(request, text, categories, isPaid, rangeStart, rangeEnd, onlyAvailable,
                sort.name(), from, size);
    }

    /**
     * Получение подробной информации об опубликованном событии по идентификатору
     * @param eventId - id события
     * @return - объект класса Event соответствующий id
     */
    @GetMapping("/{eventId}")
    public EventFullDto getById(@PathVariable Long eventId, HttpServletRequest request) {
        log.info("Получен запрос - получение информации об опубликованном событии: " + eventId);
        return eventService.getById(request, eventId);
    }
}