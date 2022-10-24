package ru.practicum.evm.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;

/**
 * DTO - пользователь
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserDto {
    private Long id; // id пользователя
    @JsonProperty(required = true)
    private String name; // имя
    @Email
    @JsonProperty(required = true)
    private String email; // почтовый адрес
}