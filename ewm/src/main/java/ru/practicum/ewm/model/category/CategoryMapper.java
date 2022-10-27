package ru.practicum.ewm.model.category;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Маппер объекта класса Category {@link ru.practicum.ewm.model.category.Category}
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public static List<CategoryDto> toCategoryDtoList(List<Category> categoryList) {
        if (categoryList == null || categoryList.isEmpty()) {
            return Collections.emptyList();
        }
        return categoryList.stream().map((CategoryMapper::toCategoryDto)).collect(Collectors.toList());
    }

    public static Category toCategoryFromNewCategoryDto(NewCategoryDto newCategoryDto) {
        Category category = new Category();
        category.setName(newCategoryDto.getName());
        return category;
    }
}