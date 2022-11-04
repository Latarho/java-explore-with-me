package ru.practicum.ewm.model.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * DTO - комментарий к событию (данные для добавления нового комментария)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCommentDto {
    @NotEmpty
    @NotBlank
    private String content;
}