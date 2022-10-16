package ru.practicum.evm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.evm.model.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}