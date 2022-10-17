package ru.practicum.evm.model.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.evm.model.category.CategoryMapper;
import ru.practicum.evm.model.stat.Stat;
import ru.practicum.evm.model.user.UserMapper;

import java.time.format.DateTimeFormatter;

/**
 * Маппер объекта класса Event {@link ru.practicum.evm.model.event.Event}
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventShortDto toEventShortDto(Event event, Stat stat) {
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                (long) event.getRequests().size(),
                event.getEventDate().format(FORMAT),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getIsPaid(),
                event.getTitle(),
                stat != null ? stat.getHits() : 0
        );
    }
}