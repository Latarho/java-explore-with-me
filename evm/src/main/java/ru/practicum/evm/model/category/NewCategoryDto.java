package ru.practicum.evm.model.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * DTO - категория (данные для добавления новой категории)
 */
@Data
public class NewCategoryDto {
    @JsonProperty(required = true)
    @NotBlank
    private String name;
}