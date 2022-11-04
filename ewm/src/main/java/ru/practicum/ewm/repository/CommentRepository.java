package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.comment.Comment;
import ru.practicum.ewm.utils.enumeration.CommentState;

import java.awt.print.Pageable;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Поиск комментария по событию и статусу
     * @param eventId id события
     * @param status состояние жизненного цикла комментария
     * @param pageable количество событий
     * @return полная информация обо всех комментариях подходящих под переданные условия
     */
    @Query("SELECT c FROM comments AS c " +
            "WHERE (c.event_id = :eventId) " +
            "AND (c.status = :status)")
    List<Comment> findByEventIdAndStatusWithPagination(Long eventId, CommentState status, Pageable pageable);
}