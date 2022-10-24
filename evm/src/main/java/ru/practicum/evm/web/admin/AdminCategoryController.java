package ru.practicum.evm.web.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.evm.model.category.CategoryDto;
import ru.practicum.evm.model.category.NewCategoryDto;
import ru.practicum.evm.service.category.CategoryService;

import javax.validation.Valid;

/**
 * Класс-контроллер работа с категориями (административная часть API)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminCategoryController {

    private final CategoryService categoryService;

    /**
     * Добавление новой категории
     * @param newCategoryDto данные добавляемой категории
     * @return объект класса Category (новая категория)
     */
    @PostMapping("/categories")
    public CategoryDto create(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Получен запрос - добавление новой категории: " + newCategoryDto.toString());
        return categoryService.create(newCategoryDto);
    }

    /**
     * Изменение категории
     * @param categoryDto данные категории для изменения
     * @return объект класса Category (категория c внесенными изменениями)
     */
    @PatchMapping("/categories")
    public CategoryDto update(@RequestBody CategoryDto categoryDto) {
        log.info("Получен запрос - изменение существующей категории: " + categoryDto.toString());
        return categoryService.update(categoryDto);
    }

    /**
     * Удаление категории (у категории не должно быть связанных событий)
     * @param catId id категории
     */
    @DeleteMapping("/categories/{catId}")
    public void delete(@PathVariable Long catId) {
        log.info("Получен запрос - удаление категории: " + catId);
        categoryService.delete(catId);
    }
}