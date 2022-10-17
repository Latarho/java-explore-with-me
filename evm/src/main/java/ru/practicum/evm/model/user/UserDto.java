package ru.practicum.evm.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * DTO - пользователь
 */
@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    @JsonProperty(required = true)
    private String name;
    @Email
    @JsonProperty(required = true)
    private String email;
}