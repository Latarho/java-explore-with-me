package ru.practicum.evm.model.request;

import lombok.Data;
import ru.practicum.evm.model.enumeration.RequestState;
import ru.practicum.evm.model.event.Event;
import ru.practicum.evm.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Модель - запрос на участие в событии
 */
@Data
@Entity(name = "Request")
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @Column(name = "created")
    private LocalDateTime createdOn;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private RequestState state;
}