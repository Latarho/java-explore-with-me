package ru.practicum.ewm.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.utils.exception.WrongRequestException;
import ru.practicum.ewm.utils.exception.RequestNotFoundException;
import ru.practicum.ewm.utils.enumeration.EventState;
import ru.practicum.ewm.utils.enumeration.RequestState;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.request.ParticipationRequestDto;
import ru.practicum.ewm.model.request.Request;
import ru.practicum.ewm.model.request.RequestMapper;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.repository.RequestRepository;
import ru.practicum.ewm.service.event.EventService;
import ru.practicum.ewm.service.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        if (eventRequests.stream().anyMatch(request -> Objects.equals(request.getRequester().getId(), userId))) {
            throw new WrongRequestException("Пользователь уже направил запрос на участие в данном событии");
        }
        if (Objects.equals(requester.getId(), event.getInitiator().getId())) {
            throw new WrongRequestException("Нельзя участвовать в своем событии");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new WrongRequestException("Событие не опубликовано, принять участие нельзя");
        }
        if (event.getParticipantLimit() != 0 && eventRequests.size() >= event.getParticipantLimit()) {
            throw new WrongRequestException("Отсутствуют свободные места на участие в событии");
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
        if (!Objects.equals(user.getId(), request.getRequester().getId())) {
            throw new WrongRequestException("Пользователь не оставлял запрос на участие в событии");
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
        if (!Objects.equals(user.getId(), event.getInitiator().getId())) {
            throw new WrongRequestException("Это событие создал другой пользователь");
        }
        return RequestMapper.toParticipationRequestDtoList(event.getRequests());
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long requestId) {
        User user = getUserOrThrow(userId);
        Event event = getEventOrThrow(eventId);
        Request request = getRequestOrThrow(requestId);
        if (!Objects.equals(user.getId(), event.getInitiator().getId())) {
            throw new WrongRequestException("Это событие создал другой пользователь");
        }
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            throw new WrongRequestException("Для участия в событии не требуется подтверждение");
        }
        if (event.getParticipantLimit() == event.getRequests().size()) {
            throw new WrongRequestException("Отсутствуют свободные места на участие в событии");
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
        if (!Objects.equals(user.getId(), event.getInitiator().getId())) {
            throw new WrongRequestException("Это событие создал другой пользователь");
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