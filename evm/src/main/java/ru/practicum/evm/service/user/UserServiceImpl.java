package ru.practicum.evm.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.evm.exception.UserNotFoundException;
import ru.practicum.evm.model.user.User;
import ru.practicum.evm.model.user.UserDto;
import ru.practicum.evm.model.user.UserMapper;
import ru.practicum.evm.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link UserService}
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto create(UserDto userDto) {
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(userDto)));
    }

    @Override
    public List<UserDto> getById(int[] ids) {
        return UserMapper.toUserDtoList(userRepository.findAllById(
                Arrays.stream(ids).boxed().collect(Collectors.toList())));
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserOrThrow(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Отсутствует пользователь id: " + userId));
    }
}