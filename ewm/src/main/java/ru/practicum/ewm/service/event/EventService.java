package ru.practicum.ewm.service.event;

import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.event.*;
import ru.practicum.ewm.utils.enumeration.EventState;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {

    EventFullDto create(NewEventDto newEventDto, Long userId);

    EventFullDto getById(HttpServletRequest request, Long eventId);

    List<EventShortDto> getEvents(HttpServletRequest request,
                                  String text,
                                  List<Long> categories,
                                  Boolean paid,
                                  String rangeStart,
                                  String rangeEnd,
                                  Boolean onlyAvailable,
                                  String sort,
                                  int from,
                                  int size);

    List<EventFullDto> getEventsAdmin(List<Long> users,
                                      List<EventState> states,
                                      List<Long> categories,
                                      String rangeStart,
                                      String rangeEnd,
                                      int from,
                                      int size);

    EventFullDto getFullEventById(Long userId, Long eventId);

    List<EventShortDto> getEventListByUserId(Long userId, int from, int size);

    EventFullDto updateEvent(EventUpdateDto eventUpdateDto, Long userId);

    EventFullDto updateEventAdmin(Long eventId, AdminUpdateEventRequestDto adminUpdateEventRequestDto);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);

    EventFullDto cancelEvent(Long userId, Long eventId);

    Event getEventOrThrow(Long eventId);

    List<Event> getEventsByCategory(Category category);

    List<Event> getAllById(List<Long> eventIdList);
}