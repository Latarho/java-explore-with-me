package ru.practicum.ewm.model.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * DTO - комментарий к событию (данные для добавления нового комментария)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NewCommentDto {
    @NotBlank
    private String content;
}