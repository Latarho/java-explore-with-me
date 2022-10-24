package ru.practicum.evm.model.location;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Маппер объекта класса Location {@link ru.practicum.evm.model.location.Location}
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationMapper {

    public static LocationDto toLocationDto(Location location) {
        return new LocationDto(
                location.getId(),
                location.getLat(),
                location.getLon()
        );
    }

    public static Location toLocation(LocationDto locationDto) {
        Location location = new Location();
        location.setLat(locationDto.getLat());
        location.setLon(locationDto.getLon());
        return location;
    }
}