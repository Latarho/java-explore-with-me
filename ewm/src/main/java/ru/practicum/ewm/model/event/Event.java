package ru.practicum.ewm.model.event;

import lombok.Data;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.utils.enumeration.EventState;
import ru.practicum.ewm.model.location.Location;
import ru.practicum.ewm.model.request.Request;
import ru.practicum.ewm.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Модель - событие
 */
@Data
@Entity(name = "Event")
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;
    @Column(name = "annotation")
    @Size(max = 2000, min = 20)
    private String annotation;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "created", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "description")
    @Size(max = 7000, min = 20)
    private String description;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;
    @Column(name = "participant_limit")
    private Long participantLimit;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private EventState state;
    @Column(name = "title")
    @Size(max = 120, min = 3)
    private String title;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "event")
    private List<Request> requests;
}