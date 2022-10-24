package ru.practicum.evm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.evm.model.request.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterId(Long requesterId);
}