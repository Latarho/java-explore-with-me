package ru.practicum.ewm.model.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DTO - комментарий к событию
 */
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class CommentDto {
    @NotNull
    private Long id;
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