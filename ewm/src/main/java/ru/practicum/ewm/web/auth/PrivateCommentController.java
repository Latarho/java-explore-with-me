package ru.practicum.ewm.web.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.comment.CommentDto;
import ru.practicum.ewm.model.comment.CommentMapper;
import ru.practicum.ewm.model.comment.NewCommentDto;
import ru.practicum.ewm.model.comment.UpdateCommentDto;
import ru.practicum.ewm.service.comment.CommentService;

import javax.validation.Valid;

/**
 * Класс-контроллер работа с комментариями (непубличная часть API)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateCommentController {

    private final CommentService commentService;

    /**
     * Добавление нового комментария
     * @param userId id текущего пользователя
     * @param eventId id события
     * @param newCommentDto данные добавляемого комментария
     * @return объект класса Comment (новый комментарий)
     */
    @PostMapping("/{userId}/events/{eventId}/comments")
    public CommentDto create(@PathVariable Long userId,
                             @PathVariable Long eventId,
                             @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("Получен запрос - добавление нового комментария: " + newCommentDto.toString() +
                 " пользователь: " + userId + " событие: " + eventId);
        return CommentMapper.toCommentDto(commentService.create(userId, eventId,
                                                                CommentMapper.toNewComment(newCommentDto)));
    }

    /**
     * Получение информации о комментарии по его идентификатору
     * @param userId id текущего пользователя
     * @param commentId id комментария
     * @return объект класса Comment соответствующий id
     */
    @GetMapping("/{userId}/comments/{commentId}")
    public CommentDto getById(@PathVariable Long userId,
                              @PathVariable Long commentId) {
        log.info("Получен запрос - получение полной информации о комментарии: " + commentId +
                  " пользователь: " + userId);
        return CommentMapper.toCommentDto(commentService.getById(userId, commentId));
    }

    /**
     * Редактирование комментария
     * @param userId id текущего пользователя
     * @param commentId id комментария
     * @param updateComment информация для редактирования комментария пользователем
     * @return объект класса Comment (комментарий с внесенными изменениями)
     */
    @PutMapping("/{userId}/comments/{commentId}")
    public CommentDto update(@PathVariable Long userId,
                             @PathVariable Long commentId,
                             @RequestBody @Validated UpdateCommentDto updateComment) {
        log.info("Получен запрос - редактирование комментария: " + commentId + " пользователь: " + userId +
                 " информация для редактирования: " + updateComment.toString());
        return CommentMapper.toCommentDto(commentService.update(userId, commentId,
                                                                CommentMapper.toUpdateComment(updateComment)));
    }

    /**
     * Удаление комментария
     * @param userId id текущего пользователя
     * @param commentId id комментария
     */
    @DeleteMapping("/{userId}/comments/{commentId}")
    public void delete(@PathVariable Long userId,
                       @PathVariable Long commentId) {
        log.info("Получен запрос - удаление комментария: " + commentId + " пользователь: " + userId);
        commentService.delete(userId, commentId);
    }
}