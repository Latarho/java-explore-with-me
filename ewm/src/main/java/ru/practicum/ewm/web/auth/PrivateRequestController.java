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

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto addRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        log.info("Получен запрос - добавление запроса на участие в чужих событиях для пользователя: " +
                userId + " событие: " + eventId);
        return requestService.create(userId, eventId);
    }

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getRequestsByUserId(@PathVariable Long userId) {
        log.info("Получен запрос - получение информации о запросах на участие в чужих событиях для пользователя: " +
                 userId);
        return requestService.getRequestsByUserId(userId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto removeRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("Получен запрос - отмена запроса на участие в чужих событиях для пользователя: " +
                userId + " запрос на участие: " + requestId);
        return requestService.removeRequest(userId, requestId);
    }
}