package ru.practicum.evm.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO - пользователь (краткая информация)
 */
@Getter
@Setter
@AllArgsConstructor
public class UserShortDto {
    private Long id; // id пользователя
    private String name; // имя
}