package ru.practicum.ewm.utils.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.practicum.ewm.utils.exception.EntityNotFoundException;

/**
 * Наследник JPA репозитория, нужен для создания метода и
 * поиска сущности с выбросом исключения, соответствующего спецификации ТЗ
 */
@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

    default T findEntityById(ID id) {
        return findById(id).orElseThrow(() ->
                new EntityNotFoundException("Отсутствует сущность: " + id));
    }
}