package ru.practicum.evm.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Модель - пользователь
 */
@Getter
@Setter
@Entity(name = "User")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id; // id пользователя
    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 255)
    private String name; // имя
    @Column(name = "email", unique = true, nullable = false)
    @Size(max = 255)
    private String email; // почтовый адрес
}