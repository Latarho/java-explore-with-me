package ru.practicum.ewm.repository;

import ru.practicum.ewm.model.request.Request;
import ru.practicum.ewm.utils.jpa.CustomJpaRepository;

import java.util.List;

public interface RequestRepository extends CustomJpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long requesterId);
}