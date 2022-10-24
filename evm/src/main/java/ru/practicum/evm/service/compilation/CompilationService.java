package ru.practicum.evm.service.compilation;

import ru.practicum.evm.model.compilation.CompilationDto;
import ru.practicum.evm.model.compilation.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    CompilationDto create(NewCompilationDto newCompilationDto);

    CompilationDto getById(Long compilationId);

    List<CompilationDto> getAllWithPagination(Boolean pinned, int from, int size);

    void delete(Long compilationId);

    void pin(Long compilationId);

    void unpin(Long compilationId);

    void addEvent(Long compilationId, Long eventId);

    void removeEvent(Long compilationId, Long eventId);
}