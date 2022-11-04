package ru.practicum.ewm.repository;

import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.utils.jpa.CustomJpaRepository;

public interface CategoryRepository extends CustomJpaRepository<Category, Long> {
}