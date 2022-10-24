package ru.practicum.ewm.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.utils.enumeration.RequestState;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Модель - заявка на участие в событии
 */
@Getter
@Setter
@ToString
@Entity(name = "Request")
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude
    private Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    @ToString.Exclude
    private User requester;
    @Column(name = "created")
    private LocalDateTime createdOn;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private RequestState state;
}