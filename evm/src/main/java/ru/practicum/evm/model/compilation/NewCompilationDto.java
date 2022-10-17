package ru.practicum.evm.model.compilation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * DTO - подборка событий (данные для добавления новой подборки событий)
 */
@Data
@AllArgsConstructor
public class NewCompilationDto {
    private List<Long> events;
    private boolean pinned;
    @JsonProperty(required = true)
    @NotBlank
    private String title;
}