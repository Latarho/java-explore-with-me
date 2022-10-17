package ru.practicum.evm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.evm.model.compilation.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
}