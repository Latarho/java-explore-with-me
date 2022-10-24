package ru.practicum.evm.model.event;

import io.micrometer.core.lang.Nullable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.evm.model.category.Category;
import ru.practicum.evm.model.category.CategoryMapper;
import ru.practicum.evm.utils.enumeration.RequestState;
import ru.practicum.evm.model.location.LocationMapper;
import ru.practicum.evm.model.request.Request;
import ru.practicum.evm.model.stat.Stats;
import ru.practicum.evm.model.user.UserMapper;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.evm.utils.constants.DateTimeConfig.FORMAT;

/**
 * Маппер объекта класса Event {@link ru.practicum.evm.model.event.Event}
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    public static EventShortDto toEventShortDto(Event event, Stats stats) {
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                (long) event.getRequests().size(),
                event.getEventDate().format(FORMAT),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getIsPaid(),
                event.getTitle(),
                stats != null ? stats.getHits() : 0
        );
    }

    public static EventFullDto eventToFullDto(Event event, @Nullable List<Request> requestList, @Nullable Stats stats) {
        return new EventFullDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                requestList != null ? requestList.stream().filter(request1 -> request1.getState() == RequestState.CONFIRMED).count() : 0,
                event.getEventDate().format(FORMAT),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getIsPaid(),
                event.getTitle(),
                stats != null ? stats.getHits() : 0,
                event.getCreatedOn().format(FORMAT),
                event.getDescription(),
                LocationMapper.toLocationDto(event.getLocation()),
                event.getParticipantLimit(),
                event.getPublishedOn() != null ? event.getPublishedOn().format(FORMAT) : null,
                event.getRequestModeration(),
                event.getState().toString()
        );
    }

    public static Event toEvent(NewEventDto newEventDto) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(LocalDateTime.parse(newEventDto.getEventDate(), FORMAT));
        event.setIsPaid(newEventDto.isPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.isRequestModeration());
        event.setTitle(newEventDto.getTitle());
        event.setLocation(LocationMapper.toLocation(newEventDto.getLocation()));
        return event;
    }

    public static Event toUpdateEvent(EventUpdateDto eventUpdateDto, Category category) {
        Event event = new Event();
        event.setAnnotation(eventUpdateDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(eventUpdateDto.getDescription());
        event.setEventDate(LocalDateTime.parse(eventUpdateDto.getEventDate(), FORMAT));
        event.setIsPaid(eventUpdateDto.getPaid());
        event.setId(eventUpdateDto.getEventId());
        event.setParticipantLimit(eventUpdateDto.getParticipantLimit());
        event.setTitle(eventUpdateDto.getTitle());
        return event;
    }

    public static Event toUpdateEventAdmin(AdminUpdateEventRequestDto adminUpdateEventRequestDto,
                                           Category category, Long eventId) {
        Event event = new Event();
        event.setAnnotation(adminUpdateEventRequestDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(adminUpdateEventRequestDto.getDescription());
        event.setEventDate(LocalDateTime.parse(adminUpdateEventRequestDto.getEventDate(), FORMAT));
        event.setIsPaid(adminUpdateEventRequestDto.getPaid());
        event.setId(eventId);
        event.setParticipantLimit(adminUpdateEventRequestDto.getParticipantLimit());
        event.setTitle(adminUpdateEventRequestDto.getTitle());
        event.setLocation(adminUpdateEventRequestDto.getLocation() != null ? LocationMapper
                .toLocation(adminUpdateEventRequestDto.getLocation()) : null);
        return event;
    }
}