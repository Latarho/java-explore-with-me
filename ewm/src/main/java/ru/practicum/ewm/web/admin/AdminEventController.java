package ru.practicum.ewm.web.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.event.AdminUpdateEventRequestDto;
import ru.practicum.ewm.model.event.EventFullDto;
import ru.practicum.ewm.service.event.EventService;
import ru.practicum.ewm.utils.enumeration.EventState;

import java.util.List;

/**
 * Класс-контроллер работа с событиями (административная часть API)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminEventController {

    private final EventService eventService;

    /**
     * Поиск событий
     * @param users список id пользователей, чьи события нужно найти
     * @param states список состояний в которых находятся искомые события
     * @param categories список id категорий в которых будет вестись поиск
     * @param rangeStart дата и время не раньше которых должно произойти событие
     * @param rangeEnd дата и время не позже которых должно произойти событие
     * @param from количество событий, которые нужно пропустить для формирования текущего набора
     * @param size количество событий в наборе
     * @return полная информация обо всех событиях подходящих под переданные условия
     */
    @GetMapping("/events")
    public List<EventFullDto> getEventsAdmin(@RequestParam List<Long> users,
                                             @RequestParam List<EventState> states,
                                             @RequestParam List<Long> categories,
                                             @RequestParam String rangeStart,
                                             @RequestParam String rangeEnd,
                                             @RequestParam int from,
                                             @RequestParam int size) {
        log.info("Получен запрос - поиск событий по параметрам (Администратор): users: " + users + " states: " + states +
                 " categories: " + categories + " rangeStart: " + rangeStart + " rangeEnd: " + rangeEnd +
                 " пропущено событий: " + from + " количество событий в наборе: " + size);
        return eventService.getEventsAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    /**
     * Редактирование события
     * @param eventId id события
     * @param adminUpdateEventRequestDto информация для редактирования события администратором
     * @return объект класса Event (событие c внесенными изменениями)
     */
    @PutMapping("/events/{eventId}")
    public EventFullDto updateEventAdmin(@PathVariable Long eventId,
                                         @RequestBody AdminUpdateEventRequestDto adminUpdateEventRequestDto) {
        log.info("Получен запрос - редактирование события (Администратор): " + eventId + " информация для редактирования: " +
                 adminUpdateEventRequestDto.toString());
        return eventService.updateEventAdmin(eventId, adminUpdateEventRequestDto);
    }

    /**
     * Публикация события
     * @param eventId id события
     * @return объект класса Event (опубликованное событие)
     */
    @PatchMapping("/events/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId) {
        log.info("Получен запрос - публикация события (Администратор): " + eventId);
        return eventService.publishEvent(eventId);
    }

    /**
     * Отклонение события
     * @param eventId id события
     * @return объект класса Event (отклоненное событие)
     */
    @PatchMapping("/events/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId) {
        log.info("Получен запрос - отклонение публикации события (Администратор): " + eventId);
        return eventService.rejectEvent(eventId);
    }
}