package ru.practicum.ewm.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.utils.client.StatsClient;
import ru.practicum.ewm.utils.exception.EntityNotFoundException;
import ru.practicum.ewm.utils.exception.WrongRequestException;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.utils.enumeration.EventState;
import ru.practicum.ewm.model.event.*;
import ru.practicum.ewm.model.stat.Stats;
import ru.practicum.ewm.model.stat.StatsDto;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.service.category.CategoryService;
import ru.practicum.ewm.service.user.UserService;
import ru.practicum.ewm.utils.helpers.PageBuilder;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.ewm.utils.constants.DateTimeConfig.FORMAT;

/**
 * Реализация интерфейса {@link EventService}
 */
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final StatsClient statsClient;

    @Override
    @Transactional
    public EventFullDto create(NewEventDto newEventDto, Long userId) {
        if (LocalDateTime.parse(newEventDto.getEventDate(), FORMAT).equals(LocalDateTime.now().plusHours(2))) {
            throw new WrongRequestException("Дата и время на которые намечено событие не может быть раньше, " +
                                                "чем через два часа от текущего момента");
        }
        User user = getUserOrThrow(userId); // пользователь существует?
        Category category = getCategoryOrThrow(newEventDto.getCategory()); // категория существует?
        Event event = EventMapper.toEvent(newEventDto);
        event.setInitiator(user);
        event.setCategory(category);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING); // у только что созданного события статус Pending
        eventRepository.save(event);
        return EventMapper.toEventFullDto(event, null, null);
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getById(HttpServletRequest request, Long eventId) {
        sendStats(request);
        Event event = getEventOrThrow(eventId); // событие существует?
        return EventMapper.toEventFullDto(event, event.getRequests(), getStatForEvent(eventId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEvents(HttpServletRequest request,
                                         String text,
                                         List<Long> categories,
                                         Boolean paid,
                                         String rangeStart,
                                         String rangeEnd,
                                         Boolean onlyAvailable,
                                         String sort,
                                         int from,
                                         int size) {
        sendStats(request);

        LocalDateTime start = (rangeStart == null) ? LocalDateTime.now() : LocalDateTime.parse(rangeStart, FORMAT);
        LocalDateTime end;
        if (rangeEnd == null) {
            end = LocalDateTime.MAX;
        } else {
            end = LocalDateTime.parse(rangeEnd, FORMAT);
        }

        List<Event> events = eventRepository.findEvents(text, categories, paid, start, end, PageBuilder.build(from, size))
                .stream()
                .collect(Collectors.toList());
        if (sort.equals("EVENT_DATE")) {
            events = events.stream()
                    .sorted(Comparator.comparing(Event::getEventDate))
                    .collect(Collectors.toList());
        }
        List<Event> eventPublishedList = events.stream()
                .filter(event -> event.getState().equals(EventState.PUBLISHED))
                .collect(Collectors.toList());

        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        mapEventsToShortDto(eventPublishedList, eventShortDtoList);

        if (sort.equals("VIEWS")) {
            eventShortDtoList = eventShortDtoList.stream()
                    .sorted(Comparator.comparing(EventShortDto::getViews))
                    .collect(Collectors.toList());
        }
        if (onlyAvailable) {
            eventShortDtoList = eventShortDtoList.stream()
                    .filter(eventShortDto -> eventShortDto.getConfirmedRequests()
                            <= getEventOrThrow(eventShortDto.getId()).getParticipantLimit())
                    .collect(Collectors.toList());
        }
        return eventShortDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> getEventsAdmin(List<Long> users, List<EventState> states, List<Long> categories,
                                             String rangeStart, String rangeEnd, int from, int size) {
        LocalDateTime start;
        if (rangeStart == null) {
            start = LocalDateTime.now();
        } else {
            start = LocalDateTime.parse(rangeStart, FORMAT);
        }
        LocalDateTime end;
        if (rangeEnd == null) {
            end = LocalDateTime.MAX;
        } else {
            end = LocalDateTime.parse(rangeEnd, FORMAT);
        }

        List<Event> events = eventRepository.findEventsByAdmin(users, states, categories, start, end, PageBuilder.build(from, size))
                .stream()
                .collect(Collectors.toList());
        List<EventFullDto> eventFullDtos = new ArrayList<>();
        for (Event event : events) {
            eventFullDtos.add(EventMapper.toEventFullDto(event, event.getRequests(), getStatForEvent(event.getId())));
        }
        return eventFullDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getFullEventById(Long userId, Long eventId) {
        getUserOrThrow(userId);
        Event event = getEventOrThrow(eventId);
        checkEventInitiator(event, userId);
        return EventMapper.toEventFullDto(event, event.getRequests(), getStatForEvent(eventId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventListByUserId(Long userId, int from, int size) {
        User user = getUserOrThrow(userId);
        List<Event> eventList = eventRepository.findEventsByInitiator(user, PageBuilder.build(from, size));
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        if (eventList.size() != 0) {
            mapEventsToShortDto(eventList, eventShortDtoList);
        }
        return eventShortDtoList;
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(EventUpdateDto eventUpdateDto, Long userId) {
        getUserOrThrow(userId); //проверка наличия пользователя
        Event eventToUpdate = getEventOrThrow(eventUpdateDto.getEventId());
        checkEventInitiator(eventToUpdate, userId);
        Category updatedCategory = new Category();
        if (eventUpdateDto.getCategory() != null) {
            updatedCategory = getCategoryOrThrow(eventUpdateDto.getCategory());
        }
        if (eventToUpdate.getState().equals(EventState.PUBLISHED)) {
            throw new WrongRequestException("Изменить можно только отмененные события или события " +
                    "в состоянии ожидания модерации");
        }
        LocalDateTime eventDate = LocalDateTime.parse(eventUpdateDto.getEventDate(), FORMAT);
        if (eventDate.equals(LocalDateTime.now().plusHours(2))) {
            throw new WrongRequestException("Дата и время на которые намечено событие не может быть раньше, " +
                    "чем через два часа от текущего момента");
        }
        if (eventToUpdate.getState().equals(EventState.CANCELED)) {
            eventToUpdate.setState(EventState.PENDING);
        }
        Event updatedEvent = EventMapper.toUpdateEvent(eventUpdateDto, updatedCategory);
        updateEventData(eventToUpdate, updatedEvent);
        if (updatedEvent.getEventDate() != null) {
            eventToUpdate.setEventDate(LocalDateTime.parse(eventUpdateDto.getEventDate(), FORMAT));
        }
        if (updatedEvent.getIsPaid() != null) {
            eventToUpdate.setIsPaid(eventUpdateDto.getPaid());
        }
        if (updatedEvent.getParticipantLimit() != null) {
            eventToUpdate.setParticipantLimit(eventUpdateDto.getParticipantLimit());
        }
        return EventMapper.toEventFullDto(eventToUpdate, eventToUpdate.getRequests(), getStatForEvent(eventToUpdate.getId()));
    }

    @Override
    @Transactional
    public EventFullDto updateEventAdmin(Long eventId, AdminUpdateEventRequestDto adminUpdateEventRequestDto) {
        Event eventToUpdate = getEventOrThrow(eventId);
        Category updatedCategory = new Category();
        if (adminUpdateEventRequestDto.getCategory() != null) {
            updatedCategory = getCategoryOrThrow(adminUpdateEventRequestDto.getCategory());
        }
        Event updatedEvent = EventMapper.toUpdateEventAdmin(adminUpdateEventRequestDto, updatedCategory, eventId);
        updateEventData(eventToUpdate, updatedEvent);
        if (updatedEvent.getEventDate() != null) {
            eventToUpdate.setEventDate(updatedEvent.getEventDate());
        }
        if (updatedEvent.getLocation() != null) {
            eventToUpdate.setLocation(updatedEvent.getLocation());
        }
        if (updatedEvent.getIsPaid() != null) {
            eventToUpdate.setIsPaid(updatedEvent.getIsPaid());
        }
        if (updatedEvent.getParticipantLimit() != null) {
            eventToUpdate.setParticipantLimit(updatedEvent.getParticipantLimit());
        }
        if (updatedEvent.getRequestModeration() != null) {
            eventToUpdate.setRequestModeration(updatedEvent.getRequestModeration());
        }
        return EventMapper.toEventFullDto(eventToUpdate, eventToUpdate.getRequests(), getStatForEvent(eventToUpdate.getId()));
    }

    @Override
    @Transactional
    public EventFullDto publishEvent(Long eventId) {
        Event event = getEventOrThrow(eventId);
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1L))) {
            throw new WrongRequestException("Дата и время на которые намечено событие не может быть раньше, " +
                    "чем через два часа от текущего момента");
        }
        if (!event.getState().equals(EventState.PENDING)) {
            throw new WrongRequestException("Нельзя опубликовать событие, не находящееся в состоянии ожидания.");
        }
        event.setPublishedOn(LocalDateTime.now());
        return changeState(eventId, EventState.PUBLISHED);
    }

    @Override
    @Transactional
    public EventFullDto rejectEvent(Long eventId) {
        Event event = getEventOrThrow(eventId);
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new WrongRequestException("Нельзя отклонить уже опубликованное событие.");
        }
        return changeState(eventId, EventState.CANCELED);
    }

    @Override
    @Transactional
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        getUserOrThrow(userId);
        Event event = getEventOrThrow(eventId);
        checkEventInitiator(event, userId);
        if (!event.getState().equals(EventState.PENDING)) {
            throw new WrongRequestException("Отменить можно только событие в состоянии ожидания модерации");
        }
        event.setState(EventState.CANCELED);
        return EventMapper.toEventFullDto(event, event.getRequests(), getStatForEvent(eventId));
    }

    @Override
    @Transactional(readOnly = true)
    public Event getEventOrThrow(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new EntityNotFoundException("Отсутствует событие id: " + eventId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getEventsByCategory(Category category) {
        return eventRepository.findEventsByCategory(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getAllById(List<Long> eventIdList) {
        return eventRepository.findAllById(eventIdList);
    }

    /**
     * Обновление атрибутов события
     * @param eventToUpdate событие для обновления
     * @param updatedEvent данные для обновления
     */
    private void updateEventData(Event eventToUpdate, Event updatedEvent) {
        if (updatedEvent.getAnnotation() != null) {
            eventToUpdate.setAnnotation(updatedEvent.getAnnotation());
        }
        if (updatedEvent.getCategory() != null) {
            eventToUpdate.setCategory(updatedEvent.getCategory());
        }
        if (updatedEvent.getDescription() != null) {
            eventToUpdate.setDescription(updatedEvent.getDescription());
        }
        if (updatedEvent.getTitle() != null) {
            eventToUpdate.setTitle(updatedEvent.getTitle());
        }
    }

    private EventFullDto changeState(Long eventId, EventState state) {
        Event event = getEventOrThrow(eventId);
        event.setState(state);
        return EventMapper.toEventFullDto(event, event.getRequests(), getStatForEvent(eventId));
    }

    /**
     * Проверка наличия категории события в базе (из categoryService)
     */
    private Category getCategoryOrThrow(Long categoryId) {
        return categoryService.getCategoryOrThrow(categoryId);
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
     * Получение статистики для события
     * @param eventId id события
     * @return объект класса Stats
     */
    private Stats getStatForEvent(Long eventId) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return statsClient.getEventStat(eventId, localDateTime.format(FORMAT), localDateTime.format(FORMAT));
    }

    /**
     * Преобразование списка Event в список EventShortDto
     * @param eventList список Event
     * @param eventShortDtoList пустой список EventShortDto
     */
    private void mapEventsToShortDto(List<Event> eventList, List<EventShortDto> eventShortDtoList) {
        for (Event event : eventList) {
            eventShortDtoList.add(EventMapper.toEventShortDto(event, getStatForEvent(event.getId())));
        }
    }

    /**
     * Проверка является ли пользователь инициатором события
     * @param event объект класса Event
     * @param userId id пользователя
     */
    private void checkEventInitiator(Event event, Long userId) {
        if (!Objects.equals(event.getInitiator().getId(), userId)) {
            throw new WrongRequestException("Пользователь id: " + userId + " не является инициатором события: "
                    + event.getId());
        }
    }

    /**
     * Создание объекта для отправки в сервис статистики.
     * @param request - запрос
     */
    private void sendStats(HttpServletRequest request) {
        StatsDto statsDto = new StatsDto(
                request.getServerName(),
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().format(FORMAT)
        );
        statsClient.send(statsDto);
    }
}