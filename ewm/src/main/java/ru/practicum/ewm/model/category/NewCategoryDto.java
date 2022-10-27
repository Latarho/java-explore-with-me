package ru.practicum.ewm.model.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * DTO - категория (данные для добавления новой категории)
 */
@Getter
@Setter
@ToString
public class NewCategoryDto {
    @JsonProperty(required = true)
    @NotBlank
    private String name; // название категории
}