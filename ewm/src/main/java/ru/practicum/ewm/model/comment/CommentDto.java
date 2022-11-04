package ru.practicum.ewm.model.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO - комментарий к событию
 */
@Data
@AllArgsConstructor
public class CommentDto {
    @NotNull
    private Long id;
    @NotEmpty
    @NotBlank
    private String content;
    @NotNull
    private Long userId;
    @NotNull
    private Long eventId;
    @NotNull
    private String created;
    @NotNull
    private String status;
}