package ru.practicum.ewm.web.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.event.EventFullDto;
import ru.practicum.ewm.model.event.EventShortDto;
import ru.practicum.ewm.model.event.EventUpdateDto;
import ru.practicum.ewm.model.event.NewEventDto;
import ru.practicum.ewm.model.request.ParticipationRequestDto;
import ru.practicum.ewm.service.event.EventService;
import ru.practicum.ewm.service.request.RequestService;

import javax.validation.Valid;
import java.util.List;

/**
 * Класс-контроллер работа с событиями (непубличная часть API)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateEventController {

    private final EventService eventService;
    private final RequestService requestService;

    @PostMapping("/{userId}/events")
    public EventFullDto create(@PathVariable Long userId, @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Получен запрос - добавление нового события: " + newEventDto.toString() + " пользователь: " + userId);
        return eventService.create(newEventDto, userId);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getFullEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Получен запрос - получение полной информации о событии: " + eventId + " пользователь: " + userId);
        return eventService.getFullEventById(userId, eventId);
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEventsOfUserById(@PathVariable Long userId,
                                                   @RequestParam(defaultValue = "0") int from,
                                                   @RequestParam(defaultValue = "10") int size) {
        log.info("Получен запрос - получение списка событий, добавленных пользователем: " + userId);
        return eventService.getEventListByUserId(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEvent(@PathVariable Long userId, @Valid @RequestBody EventUpdateDto eventUpdateDto) {
        log.info("Получен запрос - редактирование события пользователем: " + userId +
                 " информация для редактирования: " + eventUpdateDto.toString());
        return eventService.updateEvent(eventUpdateDto, userId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Получен запрос - отмена события: " + eventId + "пользователь: " + userId);
        return eventService.cancelEvent(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestForEventByUserId(@PathVariable Long userId,
                                                                    @PathVariable Long eventId) {
        log.info("Получен запрос - получение списка запросов для пользователем: " + userId +
                " событие: " + eventId);
        return requestService.getRequestForEventByUserId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable Long userId, @PathVariable Long eventId,
                                                  @PathVariable Long reqId) {
        log.info("Получен запрос - подтверждение запроса на участие для пользователя: " + userId +
                " событие: " + eventId + " запрос: " + reqId);
        return requestService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable Long userId, @PathVariable Long eventId,
                                                 @PathVariable Long reqId) {
        log.info("Получен запрос - отклонение запроса на участие для пользователя: " + userId +
                " событие: " + eventId + " запрос: " + reqId);
        return requestService.rejectRequest(userId, eventId, reqId);
    }
}