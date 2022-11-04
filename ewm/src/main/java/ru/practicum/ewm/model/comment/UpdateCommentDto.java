package ru.practicum.ewm.model.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * DTO - комментарий к событию (данные для редактирования комментария)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentDto {
    @NotEmpty
    @NotBlank
    private String content;
}