package ru.practicum.evm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.evm.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}