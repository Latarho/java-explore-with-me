package ru.practicum.ewm.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.model.comment.Comment;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.repository.CommentRepository;
import ru.practicum.ewm.service.event.EventService;
import ru.practicum.ewm.service.user.UserService;
import ru.practicum.ewm.utils.enumeration.CommentState;
import ru.practicum.ewm.utils.enumeration.EventState;
import ru.practicum.ewm.utils.exception.EntityNotFoundException;
import ru.practicum.ewm.utils.exception.WrongRequestException;
import ru.practicum.ewm.utils.helpers.PageBuilder;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EventService eventService;
    private final UserService userService;

    @Override
    @Transactional
    public Comment publishComment(Long commentId) {
        Comment comment = getCommentOrThrow(commentId);
        if (!(comment.getStatus().equals(CommentState.PENDING))) {
            return comment;
        } else {
            comment.setStatus(CommentState.PUBLISHED);
            return commentRepository.save(comment);
        }
    }

    @Override
    @Transactional
    public Comment rejectComment(Long commentId) {
        Comment comment = getCommentOrThrow(commentId);
        if (comment.getStatus().equals(CommentState.REJECTED)) {
            return comment;
        } else {
            comment.setStatus(CommentState.REJECTED);
            return commentRepository.save(comment);
        }
    }

    @Override
    @Transactional
    public Comment create(Long userId, Long eventId, Comment comment) {
        Event event = getEventOrThrow(eventId);
        User user = getUserOrThrow(userId);
        if (event.getState().equals(EventState.PENDING)) {
            throw new WrongRequestException("Событие не опубликовано, оставить комментарий нельзя");
        }
            comment.setUser(user);
            comment.setEvent(event);
            comment.setCreated(LocalDateTime.now());
            comment.setStatus(CommentState.PENDING);
            return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment update(Long userId, Long commentId, Comment comment) {
        Comment commentToUpdate = getCommentOrThrow(commentId);
        getUserOrThrow(userId);
        checkCommentInitiator(commentToUpdate, userId);
        if (commentToUpdate.getStatus().equals(CommentState.PUBLISHED)) {
            throw new WrongRequestException("Изменить можно только отмененные комментарии или комментарии " +
                    "в состоянии ожидания модерации");
        }
        commentToUpdate.setContent(comment.getContent());
        commentToUpdate.setStatus(CommentState.PENDING);
        return commentRepository.save(commentToUpdate);
    }

    @Override
    @Transactional
    public void delete(Long userId, Long commentId) {
        Comment commentToDelete = getCommentOrThrow(commentId);
        checkCommentInitiator(commentToDelete, userId);
        commentRepository.delete(commentToDelete);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getById(Long userId, Long commentId) {
        Comment comment = getCommentOrThrow(commentId);
        checkCommentInitiator(comment, userId);
        return comment;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllWithPagination(Long eventId, int from, int size) {
        getEventOrThrow(eventId);
        return commentRepository.findByEventIdAndStatusWithPagination(eventId, CommentState.PUBLISHED,
                (Pageable) PageBuilder.build(from, size));
    }

    private Comment getCommentOrThrow(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new EntityNotFoundException("Отсутствует комментарий: " + commentId));
    }

    /**
     * Проверка наличия пользователя в базе (из userService)
     * @param userId id пользователя
     * @return объект класса User
     */
    private User getUserOrThrow(Long userId) {
        return userService.getUserOrThrow(userId);
    }

    /**
     * Проверка наличия события в базе (из eventService)
     * @param eventId id события
     * @return объект класса Event
     */
    private Event getEventOrThrow(Long eventId) {
        return eventService.getEventOrThrow(eventId);
    }

    /**
     * Проверка оставлял ли пользователь комментарий для события
     * @param comment объект класса Comment
     * @param userId id пользователя
     */
    private void checkCommentInitiator(Comment comment, Long userId) {
        if (!Objects.equals(comment.getUser().getId(), userId)) {
            throw new WrongRequestException("Пользователь id: " + userId + " не оставлял комментарий для события: "
                    + comment.getId());
        }
    }
}