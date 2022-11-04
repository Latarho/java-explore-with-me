package ru.practicum.ewm.web.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.request.ParticipationRequestDto;
import ru.practicum.ewm.service.request.RequestService;

import java.util.List;

/**
 * Класс-контроллер работа с запросами (непубличная часть API)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateRequestController {

    private final RequestService requestService;

    /**
     * Добавление заявки от текущего пользователя на участие в событии
     * @param userId id текущего пользователя
     * @param eventId id события
     * @return объект класса Request (новая заявка на участие в событии)
     */
    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto addRequest(@PathVariable Long userId,
                                              @RequestParam Long eventId) {
        log.info("Получен запрос - добавление запроса на участие в чужих событиях для пользователя: " +
                userId + " событие: " + eventId);
        return requestService.create(userId, eventId);
    }

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     * @param userId id текущего пользователя
     * @return список заявок
     */
    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getRequestsByUserId(@PathVariable Long userId) {
        log.info("Получен запрос - получение информации о запросах на участие в чужих событиях для пользователя: " +
                 userId);
        return requestService.getRequestsByUserId(userId);
    }

    /**
     * Отмена своей заявки на участие в событии
     * @param userId id текущего пользователя
     * @param requestId id запроса на участие
     * @return объект класса Request (отмененная заявка на участие в событии)
     */
    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto removeRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId) {
        log.info("Получен запрос - отмена запроса на участие в чужих событиях для пользователя: " +
                userId + " запрос на участие: " + requestId);
        return requestService.removeRequest(userId, requestId);
    }
}