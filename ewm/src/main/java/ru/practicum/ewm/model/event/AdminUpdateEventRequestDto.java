package ru.practicum.ewm.model.event;

import lombok.*;
import ru.practicum.ewm.model.location.LocationDto;

/**
 * Dto - событие (информация для редактирования события администратором)
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class AdminUpdateEventRequestDto {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private LocationDto location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private String title;
}