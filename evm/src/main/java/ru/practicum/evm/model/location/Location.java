package ru.practicum.evm.model.location;

import lombok.Data;

import javax.persistence.*;

/**
 * Модель - широта и долгота места проведения события
 */
@Data
@Entity(name = "Location")
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;
    @Column(name = "latitude")
    private float latitude;
    @Column(name = "longitude")
    private float longitude;
}