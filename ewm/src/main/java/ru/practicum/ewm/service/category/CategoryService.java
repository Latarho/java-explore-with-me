package ru.practicum.ewm.service.category;

import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.model.category.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto create(NewCategoryDto newCategoryDto);

    CategoryDto getById(Long categoryId);

    List<CategoryDto> getAllWithPagination(int from, int size);

    CategoryDto update(CategoryDto categoryDto);

    void delete(Long categoryId);

    Category getCategoryOrThrow(Long categoryId);
}