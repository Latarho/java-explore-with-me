package ru.practicum.evm.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.evm.utils.exception.ConditionsNotMetException;
import ru.practicum.evm.utils.exception.RequestNotFoundException;
import ru.practicum.evm.utils.enumeration.EventState;
import ru.practicum.evm.utils.enumeration.RequestState;
import ru.practicum.evm.model.event.Event;
import ru.practicum.evm.model.request.ParticipationRequestDto;
import ru.practicum.evm.model.request.Request;
import ru.practicum.evm.model.request.RequestMapper;
import ru.practicum.evm.model.user.User;
import ru.practicum.evm.repository.RequestRepository;
import ru.practicum.evm.service.event.EventService;
import ru.practicum.evm.service.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventService eventService;
    private final UserService userService;

    @Override
    @Transactional
    public ParticipationRequestDto create(Long userId, Long eventId) {
        Event event = getEventOrThrow(eventId);
        User requester = getUserOrThrow(userId);
        Request newRequest = new Request();
        newRequest.setEvent(event);
        newRequest.setRequester(requester);
        newRequest.setCreatedOn(LocalDateTime.now());
        newRequest.setState(RequestState.PENDING);
        List<Request> eventRequests = event.getRequests();
        if (eventRequests.stream().anyMatch(request -> request.getRequester().getId() == userId)) {
            throw new ConditionsNotMetException("Пользователь уже направил запрос на участие в данном событии");
        }
        if (requester.getId() == event.getInitiator().getId()) {
            throw new ConditionsNotMetException("Нельзя участвовать в своем событии");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConditionsNotMetException("Событие не опубликовано, принять участие нельзя");
        }
        if (event.getParticipantLimit() != 0 && eventRequests.size() >= event.getParticipantLimit()) {
            throw new ConditionsNotMetException("Отсутствуют свободные места на участие в событии");
        }
        if (!event.getRequestModeration()) {
            newRequest.setState(RequestState.CONFIRMED);
        }
        requestRepository.save(newRequest);
        return RequestMapper.toParticipationRequestDto(newRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getRequestsByUserId(Long userId) {
        getUserOrThrow(userId);
        return RequestMapper.toParticipationRequestDtoList(requestRepository.findAllByRequesterId(userId));
    }

    @Override
    @Transactional
    public ParticipationRequestDto removeRequest(Long userId, Long requestId) {
        Request request = getRequestOrThrow(requestId);
        User user = getUserOrThrow(userId);
        if (user.getId() != request.getRequester().getId()) {
            throw new ConditionsNotMetException("Пользователь не оставлял запрос на участие в событии");
        }
        requestRepository.delete(request);
        request.setState(RequestState.CANCELED);
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getRequestForEventByUserId(Long userId, Long eventId) {
        User user = getUserOrThrow(userId);
        Event event = getEventOrThrow(eventId);
        if (user.getId() != event.getInitiator().getId()) {
            throw new ConditionsNotMetException("Это событие создал другой пользователь");
        }
        return RequestMapper.toParticipationRequestDtoList(event.getRequests());
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long requestId) {
        User user = getUserOrThrow(userId);
        Event event = getEventOrThrow(eventId);
        Request request = getRequestOrThrow(requestId);
        if (user.getId() != event.getInitiator().getId()) {
            throw new ConditionsNotMetException("Это событие создал другой пользователь");
        }
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            throw new ConditionsNotMetException("Для участия в событии не требуется подтверждение");
        }
        if (event.getParticipantLimit() == event.getRequests().size()) {
            throw new ConditionsNotMetException("Отсутствуют свободные места на участие в событии");
        }
        request.setState(RequestState.CONFIRMED);
        List<Request> requestList = event.getRequests();
        long approvedCount = requestList.stream().filter(request1 -> request1.getState() == RequestState.CONFIRMED).count();
        if (event.getParticipantLimit() == approvedCount + 1) {
            for (Request request1 : requestList) {
                if (request1.getState().equals(RequestState.PENDING))
                    request1.setState(RequestState.REJECTED);
            }
        }
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long requestId) {
        User user = getUserOrThrow(userId);
        Event event = getEventOrThrow(eventId);
        Request request = getRequestOrThrow(requestId);
        if (user.getId() != event.getInitiator().getId()) {
            throw new ConditionsNotMetException("Это событие создал другой пользователь");
        }
        request.setState(RequestState.REJECTED);
        return RequestMapper.toParticipationRequestDto(request);
    }

    private Request getRequestOrThrow(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(() ->
                new RequestNotFoundException("Отсутствует запрос: " + requestId));
    }

    /**
     * Проверка наличия пользователя в базе (из userService)
     * @param userId id пользователя
     * @return объект класса User
     */
    private User getUserOrThrow(Long userId) {
        return userService.getUserOrThrow(userId);
    }

    /**
     * Проверка наличия пользователя в базе (из userService)
     * @param eventId id события
     * @return объект класса Event
     */
    private Event getEventOrThrow(Long eventId) {
        return eventService.getEventOrThrow(eventId);
    }
}