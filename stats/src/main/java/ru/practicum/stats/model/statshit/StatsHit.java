package ru.practicum.stats.model.statshit;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.stats.model.app.App;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Модель - информация о записи (запрос к эндпойнту)
 */
@Data
@RequiredArgsConstructor
@Entity(name = "StatsHit")
@Table(name = "statistics")
public class StatsHit {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id; // Идентификатор записи
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "app_id", nullable = false)
    private App app; // Сервис для которого записывается информация
    @Column(name = "uri", nullable = false)
    private String uri; // URI для которого был осуществлен запрос
    @Column(name = "ip", nullable = false)
    private String ip; // IP-адрес пользователя, осуществившего запрос
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp; // Дата и время, когда был совершен запрос к эндпоинту
}