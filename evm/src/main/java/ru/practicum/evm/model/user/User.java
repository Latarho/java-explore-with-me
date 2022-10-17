package ru.practicum.evm.model.user;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Модель - пользователь
 */
@Data
@Entity(name = "User")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;
    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 50)
    private String name;
    @Column(name = "email", unique = true, nullable = false)
    @Size(max = 255)
    private String email;
}