package ru.practicum.evm.model.compilation;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.evm.model.event.Event;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Модель - подборка событий
 */
@Getter
@Setter
@Entity(name = "Compilation")
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id; // id подборки
    @Column(name = "title")
    private String title; // заголовок подборки
    @Column(name = "pinned")
    private boolean pinned; // закреплена ли подборка на главной странице сайта
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "compilations_events",
               joinColumns = {@JoinColumn(name = "compilation_id")},
               inverseJoinColumns = {@JoinColumn(name = "event_id")})
    private Set<Event> events = new HashSet<>(); // список событий входящих в подборку
}