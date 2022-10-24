package ru.practicum.ewm.model.location;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Модель - широта и долгота места проведения события
 */
@Getter
@Setter
@Entity(name = "Location")
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id; // id места проведения события
    @Column(name = "latitude")
    private float lat; // широта
    @Column(name = "longitude")
    private float lon; // долгота
}