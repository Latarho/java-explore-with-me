package ru.practicum.ewm.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * Dto - событие
 */
@Data
@AllArgsConstructor
public class EventUpdateDto {
    @JsonProperty(required = true)
    private Long eventId;
    @Size(min = 20, max = 2000)
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private Boolean paid;
    @Size(min = 3, max = 120)
    private String title;
    private Long participantLimit;
}