package ru.practicum.evm.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.evm.exception.CategoryHaveEventsException;
import ru.practicum.evm.exception.CategoryNotFoundException;
import ru.practicum.evm.model.category.Category;
import ru.practicum.evm.model.category.CategoryDto;
import ru.practicum.evm.model.category.CategoryMapper;
import ru.practicum.evm.model.category.NewCategoryDto;
import ru.practicum.evm.repository.CategoryRepository;

import java.util.List;

/**
 * Реализация интерфейса {@link CategoryService}
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

//    private final EventService eventService;

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.save(CategoryMapper.toCategoryFromNewCategoryDto(newCategoryDto));
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto getById(int categoryId) {
        Category category = getCategoryOrThrow(categoryId);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAll(int from, int size) {
        Page<Category> categoryPage = categoryRepository.findAll(PageRequest.of(from / size, size));
        return CategoryMapper.toCategoryDtoList(categoryPage.getContent());
    }

    @Override
    @Transactional
    public CategoryDto update(CategoryDto categoryDto) {
        Category category = getCategoryOrThrow(categoryDto.getId());
        category.setName(categoryDto.getName());
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional
    public void delete(int categoryId) {
//        List<Event> events = eventService.getEventsByCategory(getCategoryOrThrow(categoryId));
//        if (!events.isEmpty()) {
//            throw new CategoryHaveEventsException("Нельзя удалить категорию id (с ней связаны события): ");
//        }
//        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Category getCategoryOrThrow(int categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Отсутствует категория id: " + categoryId));
    }
}