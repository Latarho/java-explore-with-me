package ru.practicum.evm.service.request;

import ru.practicum.evm.model.request.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    ParticipationRequestDto create(Long userId, Long eventId);

    List<ParticipationRequestDto> getRequestsByUserId(Long userId);

    ParticipationRequestDto removeRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getRequestForEventByUserId(Long userId, Long eventId);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long requestId);

    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long requestId);
}