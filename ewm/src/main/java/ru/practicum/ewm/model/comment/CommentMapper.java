package ru.practicum.ewm.model.comment;

import java.time.LocalDateTime;

import static ru.practicum.ewm.utils.constants.DateTimeConfig.FORMAT;

/**
 * Маппер объекта класса Comment {@link ru.practicum.ewm.model.comment.Comment}
 */
public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUser().getId())
                .eventId(comment.getEvent().getId())
                .created(LocalDateTime.now().format(FORMAT))
                .status(comment.getStatus().toString())
                .build();
    }

    public static Comment toNewComment(NewCommentDto newCommentDto) {
        return Comment.builder()
                .content(newCommentDto.getContent())
                .build();
    }

    public static Comment toUpdateComment(UpdateCommentDto updateCommentDto) {
        return Comment.builder()
                .content(updateCommentDto.getContent())
                .build();
    }
}