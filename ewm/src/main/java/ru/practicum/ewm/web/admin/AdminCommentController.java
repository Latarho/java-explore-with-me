package ru.practicum.ewm.web.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.comment.CommentDto;
import ru.practicum.ewm.model.comment.CommentMapper;
import ru.practicum.ewm.service.comment.CommentService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс-контроллер работа с комментариями (административная часть API)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminCommentController {

    private final CommentService commentService;

    /**
     * Публикация комментария
     * @param commentId id комментария
     * @return объект класса Comment (опубликованный комментарий)
     */
    @PatchMapping("/comments/{commentId}/publish")
    public CommentDto publishComment(@PathVariable Long commentId) {
        log.info("Получен запрос - публикация комментария (Администратор): " + commentId);
        return CommentMapper.toCommentDto(commentService.publishComment(commentId));
    }

    /**
     * Отклонение комментария
     * @param commentId id комментария
     * @return объект класса Comment (отклоненный комментарий)
     */
    @PatchMapping("/comments/{commentId}/reject")
    public CommentDto rejectComment(@PathVariable Long commentId) {
        log.info("Получен запрос - отклонение публикации комментария (Администратор): " + commentId);
        return CommentMapper.toCommentDto(commentService.rejectComment(commentId));
    }

    /**
     * Получение списка комментариев для события (комментарии в статусе опубликован)
     * @param eventId id события
     * @param from количество комментариев, которые нужно пропустить для формирования текущего набора
     * @param size количество комментариев в наборе
     * @return список комментариев
     */
    @GetMapping("/events/{eventId}/comments")
    public List<CommentDto> getAllWithPagination(@PathVariable Long eventId,
                                                 @RequestParam(defaultValue = "0") int from,
                                                 @RequestParam(defaultValue = "10") int size) {
        log.info("Получен запрос - получение списка комментариев для события (Администратор): " + eventId +
                 " в списке элементов: " + size);
        return commentService.getAllWithPagination(eventId, from, size)
                             .stream()
                             .map(CommentMapper::toCommentDto)
                             .collect(Collectors.toList());
    }
}