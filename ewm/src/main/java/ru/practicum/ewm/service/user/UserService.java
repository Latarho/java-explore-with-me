package ru.practicum.ewm.service.user;

import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.model.user.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(UserDto userDto);

    List<UserDto> getById(List<Long> ids);

    void delete(Long id);

    User getUserOrThrow(Long userId);
}