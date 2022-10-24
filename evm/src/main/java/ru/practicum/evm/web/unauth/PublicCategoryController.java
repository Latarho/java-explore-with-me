package ru.practicum.evm.web.unauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.evm.model.category.CategoryDto;
import ru.practicum.evm.service.category.CategoryService;

import java.util.List;

/**
 * Класс-контроллер работа с категориями (публичная часть API)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {

    private final CategoryService categoryService;

    /**
     * Получение категорий
     * @param from количество категорий, которые нужно пропустить для формирования текущего набора
     * @param size количество категорий в наборе
     * @return список категорий
     */
    @GetMapping
    public List<CategoryDto> getAllWithPagination(@RequestParam int from, @RequestParam int size) {
        log.info("Получен запрос - получение списка категорий, в списке элементов: " + size);
        return categoryService.getAllWithPagination(from, size);
    }

    /**
     * Получение информации о категории по ее идентификатору
     * @param catId id категории
     * @return объект класса Category соответствующий id
     */
    @GetMapping("/{catId}")
    public CategoryDto getById(@PathVariable Long catId) {
        log.info("Получен запрос - получение информации о категории: " + catId);
        return categoryService.getById(catId);
    }
}