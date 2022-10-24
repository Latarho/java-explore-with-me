package ru.practicum.ewm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.utils.enumeration.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event AS e " +
            "WHERE ((:text) IS NULL " +
            "OR UPPER(e.annotation) LIKE UPPER(CONCAT('%', :text, '%')) " +
            "OR UPPER(e.description) LIKE UPPER(CONCAT('%', :text, '%'))) " +
            "AND ((:categories) IS NULL OR e.category.id IN :categories) " +
            "AND ((:paid) IS NULL OR e.isPaid = :paid) " +
            "AND (e.eventDate >= :start) " +
            "AND ( e.eventDate <= :end)")
    Page<Event> findEvents(String text, List<Long> categories, Boolean paid, LocalDateTime start,
                             LocalDateTime end, Pageable pageable);

    @Query("SELECT e FROM Event AS e " +
            "WHERE ((:users) IS NULL OR e.initiator.id IN :users) " +
            "AND ((:states) IS NULL OR e.state IN :states) " +
            "AND ((:categories) IS NULL OR e.category.id IN :categories) " +
            "AND (e.eventDate >= :start) " +
            "AND ( e.eventDate <= :end)")
    Page<Event> findEventsByAdmin(List<Long> users, List<EventState> states, List<Long> categories,
                                    LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Event> findEventsByInitiator(User initiator, Pageable pageable);

    List<Event> findEventsByCategory(Category category);
}