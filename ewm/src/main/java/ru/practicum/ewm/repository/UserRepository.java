package ru.practicum.ewm.repository;

import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.utils.jpa.CustomJpaRepository;

public interface UserRepository extends CustomJpaRepository<User, Long> {
}