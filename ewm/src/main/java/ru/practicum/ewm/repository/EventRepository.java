package ru.practicum.ewm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.utils.enumeration.EventState;
import ru.practicum.ewm.utils.jpa.CustomJpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends CustomJpaRepository<Event, Long> {

    /**
     * Поиск события
     * @param text текст для поиска в содержимом аннотации и подробном описании события
     * @param categories список id категорий в которых будет вестись поиск
     * @param paid поиск только платных/бесплатных событий
     * @param start дата и время не раньше которых должно произойти событие
     * @param end дата и время не позже которых должно произойти событие
     * @param pageable количество событий
     * @return полная информация обо всех событиях подходящих под переданные условия
     */
    @Query("SELECT e FROM Event AS e " +
            "WHERE ((:text) IS NULL " +
            "OR UPPER(e.annotation) LIKE UPPER(CONCAT('%', :text, '%')) " +
            "OR UPPER(e.description) LIKE UPPER(CONCAT('%', :text, '%'))) " +
            "AND ((:categories) IS NULL OR e.category.id IN :categories) " +
            "AND ((:paid) IS NULL OR e.isPaid = :paid) " +
            "AND (e.eventDate BETWEEN :start AND :end)")
    Page<Event> findEvents(String text, List<Long> categories, Boolean paid, LocalDateTime start,
                             LocalDateTime end, Pageable pageable);

    /**
     * Поиск события (администратор)
     * @param users список id пользователей, чьи события нужно найти
     * @param states список состояний в которых находятся искомые события
     * @param categories список id категорий в которых будет вестись поиск
     * @param start дата и время не раньше которых должно произойти событие
     * @param end дата и время не позже которых должно произойти событие
     * @param pageable количество событий
     * @return полная информация обо всех событиях подходящих под переданные условия
     */
    @Query("SELECT e FROM Event AS e " +
            "WHERE ((:users) IS NULL OR e.initiator.id IN :users) " +
            "AND ((:states) IS NULL OR e.state IN :states) " +
            "AND ((:categories) IS NULL OR e.category.id IN :categories) " +
            "AND (e.eventDate BETWEEN :start AND :end)")
    Page<Event> findEventsByAdmin(List<Long> users, List<EventState> states, List<Long> categories,
                                    LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Event> findEventsByInitiator(User initiator, Pageable pageable);

    List<Event> findEventsByCategory(Category category);
}