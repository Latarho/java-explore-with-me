package ru.practicum.ewm.utils.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageBuilder {

    public static Pageable build(int from, int size) {
        if (size != 0) {
            return PageRequest.of(from / size, size);
        }
        return Pageable.unpaged();
    }
}