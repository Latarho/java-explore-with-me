package ru.practicum.ewm.service.comment;

import ru.practicum.ewm.model.comment.Comment;

import java.util.List;

public interface CommentService {

    public Comment create(Long userId, Long eventId, Comment comment);

    public Comment getById(Long userId, Long commentId);

    public Comment update(Long userId, Long commentId, Comment comment);

    public void delete(Long userId, Long commentId);

    public Comment publishComment(Long commentId);

    public Comment rejectComment(Long commentId);

    public List<Comment> getAllWithPagination(Long eventId, int from, int size);
}