package ru.practicum.ewm.model.comment;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static ru.practicum.ewm.utils.constants.DateTimeConfig.FORMAT;

/**
 * Маппер объекта класса Comment {@link ru.practicum.ewm.model.comment.Comment}
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getEvent().getId(),
                LocalDateTime.now().format(FORMAT),
                comment.getStatus().toString()
        );
    }

    public static Comment toNewComment(NewCommentDto newCommentDto) {
        return new Comment(
                null,
                newCommentDto.getContent(),
                null,
                null,
                null,
                null
        );
    }

    public static Comment toUpdateComment(UpdateCommentDto updateCommentDto) {
        return new Comment(
                null,
                updateCommentDto.getContent(),
                null,
                null,
                null,
                null
        );
    }
}