package ru.practicum.evm.service.user;

import ru.practicum.evm.model.user.User;
import ru.practicum.evm.model.user.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    List<UserDto> getById(int[] ids);

    void delete(int id);

    User getUserOrThrow(int userId); //служебный метод для проверки наличия пользователя в базе
}