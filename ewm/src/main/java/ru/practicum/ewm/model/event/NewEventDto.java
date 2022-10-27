package ru.practicum.ewm.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.model.location.LocationDto;

import javax.validation.constraints.Size;

/**
 * DTO - событие (данные для добавления нового события)
 */
@Data
@AllArgsConstructor
public class NewEventDto {
    @JsonProperty(required = true)
    @Size(min = 20, max = 2000)
    private String annotation;
    @JsonProperty(required = true)
    private Long category;
    @JsonProperty(required = true)
    @Size(min = 20, max = 7000)
    private String description;
    @JsonProperty(required = true)
    private String eventDate;
    @JsonProperty(required = true)
    private LocationDto location;
    @JsonProperty(defaultValue = "false")
    private boolean paid;
    @JsonProperty(defaultValue = "0")
    private Long participantLimit;
    @JsonProperty(defaultValue = "false")
    private boolean requestModeration;
    @JsonProperty(required = true)
    @Size(min = 3, max = 120)
    private String title;
}