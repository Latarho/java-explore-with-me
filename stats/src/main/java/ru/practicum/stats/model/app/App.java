package ru.practicum.stats.model.app;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Модель - приложение
 */
@Data
@RequiredArgsConstructor
@Entity(name = "App")
@Table(name = "applications")
public class App {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id; // Идентификатор записи
    @Column(name = "app", nullable = false)
    private String appName; // Название сервиса
}