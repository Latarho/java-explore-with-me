package ru.practicum.evm.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Dto - заявка на участие в событии
 */
@Getter
@Setter
@AllArgsConstructor
public class ParticipationRequestDto {
    @JsonProperty(required = true)
    private String created; // дата и время создания заявки
    @JsonProperty(required = true)
    private Long event; // идентификатор события
    @JsonProperty(required = true)
    private Long id; // идентификатор заявки
    @JsonProperty(required = true)
    private Long requester; // идентификатор пользователя, отправившего заявку
    @JsonProperty(required = true)
    private String status; // статус заявки
}