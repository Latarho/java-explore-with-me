package ru.practicum.evm.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.evm.utils.exception.CategoryHaveEventsException;
import ru.practicum.evm.utils.exception.CategoryNotFoundException;
import ru.practicum.evm.model.category.Category;
import ru.practicum.evm.model.category.CategoryDto;
import ru.practicum.evm.model.category.CategoryMapper;
import ru.practicum.evm.model.category.NewCategoryDto;
import ru.practicum.evm.model.event.Event;
import ru.practicum.evm.repository.CategoryRepository;
import ru.practicum.evm.service.event.EventService;

import java.util.List;

/**
 * Реализация интерфейса {@link CategoryService}
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventService eventService;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, @Lazy EventService eventService) {
        this.categoryRepository = categoryRepository;
        this.eventService = eventService;
    }

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.save(CategoryMapper.toCategoryFromNewCategoryDto(newCategoryDto));
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getById(Long categoryId) {
        Category category = getCategoryOrThrow(categoryId);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllWithPagination(int from, int size) {
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
    public void delete(Long categoryId) {
        List<Event> events = eventService.getEventsByCategory(getCategoryOrThrow(categoryId));
        if (!events.isEmpty()) {
            throw new CategoryHaveEventsException("Нельзя удалить категорию (с ней связаны события): " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryOrThrow(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() ->
                new CategoryNotFoundException("Отсутствует категория: " + categoryId));
    }
}