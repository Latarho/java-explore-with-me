package ru.practicum.ewm.model.request;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.utils.constants.DateTimeConfig.FORMAT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return new ParticipationRequestDto(
                LocalDateTime.now().format(FORMAT),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                request.getState().toString()
        );
    }

    public static List<ParticipationRequestDto> toParticipationRequestDtoList(List<Request> requestList) {
        if (requestList == null || requestList.isEmpty()) {
            return Collections.emptyList();
        }
        return requestList.stream().map((RequestMapper::toParticipationRequestDto)).collect(Collectors.toList());
    }
}