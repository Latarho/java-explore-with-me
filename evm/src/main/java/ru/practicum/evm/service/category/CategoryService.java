package ru.practicum.evm.service.category;

import ru.practicum.evm.model.category.Category;
import ru.practicum.evm.model.category.CategoryDto;
import ru.practicum.evm.model.category.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(NewCategoryDto newCategoryDto);

    CategoryDto getById(int categoryId);

    List<CategoryDto> getAll(int from, int size);

    CategoryDto update(CategoryDto categoryDto);

    void delete(int categoryId);

    Category getCategoryOrThrow(int categoryId); //служебный метод для проверки наличия категории в базе
}