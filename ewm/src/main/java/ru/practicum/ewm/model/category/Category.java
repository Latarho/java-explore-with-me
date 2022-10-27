package ru.practicum.ewm.model.category;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Модель - категория
 */
@Getter
@Setter
@Entity(name = "Category")
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id; // id категории
    @Column(name = "name", unique = true, nullable = false)
    private String name; // название категории
}