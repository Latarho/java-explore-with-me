package ru.practicum.evm.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO - пользователь (краткая информация)
 */
@Data
@AllArgsConstructor
public class UserShortDto {
    private Integer id;
    private String name;
}