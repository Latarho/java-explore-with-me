package ru.practicum.evm.web.admin.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.evm.model.user.UserDto;
import ru.practicum.evm.service.user.UserService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * Класс-контроллер работа с пользователями (административная часть API)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminUserController {

    private final UserService userService;

    /**
     * Добавление нового пользователя
     * @param userDto данные добавляемого пользователя
     * @return объект класса User (новый пользователь)
     */
    @PostMapping("/users")
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        log.info("Получен запрос - добавление нового пользователя: " + userDto.toString());
        return userService.create(userDto);
    }

    /**
     * Получение информации о пользователях по переданному id
     * @param ids id пользователей
     * @return список пользователей
     */
    @GetMapping("/users")
    public List<UserDto> getById(@RequestParam Long[] ids) {
        log.info("Получен запрос - получение информации о пользователях: " + Arrays.toString(ids));
        return userService.getById(ids);
    }

    /**
     * Удаление пользователя по переданному id
     * @param id id пользователя
     */
    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Получен запрос - удаление пользователя: " + id);
        userService.delete(id);
    }
}