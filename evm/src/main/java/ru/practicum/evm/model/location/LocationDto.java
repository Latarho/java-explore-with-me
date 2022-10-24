package ru.practicum.evm.model.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Dto - широта и долгота места проведения события
 */
@Getter
@Setter
@AllArgsConstructor
public class LocationDto {
    private Long id; // id места проведения события
    private float lat; // широта
    private float lon; // долгота
}