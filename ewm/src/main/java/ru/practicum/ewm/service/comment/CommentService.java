package ru.practicum.ewm.service.comment;

import org.springframework.data.domain.Page;
import ru.practicum.ewm.model.comment.Comment;

public interface CommentService {

    Comment create(Long userId, Long eventId, Comment comment);

    Comment getById(Long userId, Long commentId);

    Comment update(Long userId, Long commentId, Comment comment);

    void delete(Long userId, Long commentId);

    Comment publishComment(Long commentId);

    Comment rejectComment(Long commentId);

    Page<Comment> getAllWithPagination(Long eventId, int from, int size);
}