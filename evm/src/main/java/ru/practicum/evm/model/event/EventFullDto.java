package ru.practicum.evm.model.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.evm.model.category.CategoryDto;
import ru.practicum.evm.model.location.LocationDto;
import ru.practicum.evm.model.user.UserShortDto;

/**
 * Dto - событие (полная информация)
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EventFullDto extends EventShortDto {
    private String createdOn;
    private String description;
    private LocationDto location;
    private Long participantLimit;
    private String publishedOn;
    private boolean requestModeration;
    private String state;

    public EventFullDto(
            Long id,
            String annotation,
            CategoryDto category,
            Long confirmedRequests,
            String eventDate,
            UserShortDto initiator,
            boolean paid,
            String title,
            Long views,
            String createdOn,
            String description,
            LocationDto location,
            Long participantLimit,
            String publishedOn,
            boolean requestModeration,
            String state
    ) {
        super(id, annotation, category, confirmedRequests, eventDate, initiator, paid, title, views);
        this.createdOn = createdOn;
        this.description = description;
        this.location = location;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        this.requestModeration = requestModeration;
        this.state = state;
    }
}