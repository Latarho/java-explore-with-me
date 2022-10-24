package ru.practicum.ewm.model.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DTO - категория
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class CategoryDto {
    @NotNull
    private Long id; // id категории
    @NotBlank
    private String name; // название категории
}