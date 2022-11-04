package ru.practicum.ewm.repository;

import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.utils.jpa.CustomJpaRepository;

public interface CompilationRepository extends CustomJpaRepository<Compilation, Long> {
}