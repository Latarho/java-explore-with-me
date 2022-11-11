package ru.practicum.ewm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.comment.Comment;
import ru.practicum.ewm.utils.enumeration.CommentState;
import ru.practicum.ewm.utils.jpa.CustomJpaRepository;

public interface CommentRepository extends CustomJpaRepository<Comment, Long> {

    /**
     * Поиск комментария по событию и статусу
     * @param eventId id события
     * @param status состояние жизненного цикла комментария
     * @param pageable количество событий
     * @return полная информация обо всех комментариях подходящих под переданные условия
     */
    @Query("SELECT c FROM Comment AS c " +
            "WHERE (c.event.id = :eventId) " +
            "AND (c.status = :status)")
    Page<Comment> findByEventIdAndStatusWithPagination(Long eventId, CommentState status, Pageable pageable);
}