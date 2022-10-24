package ru.practicum.ewm.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.utils.exception.UserNotFoundException;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.model.user.UserDto;
import ru.practicum.ewm.model.user.UserMapper;
import ru.practicum.ewm.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link UserService}
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(userDto)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getById(List<Long> ids) {
        return UserMapper.toUserDtoList(userRepository.findAllById(
                Arrays.stream(ids.toArray(new Long[0])).collect(Collectors.toList())));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserOrThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Отсутствует пользователь id: " + userId));
    }
}