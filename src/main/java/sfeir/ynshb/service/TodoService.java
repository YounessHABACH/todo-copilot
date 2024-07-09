package sfeir.ynshb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sfeir.ynshb.dto.TodoDto;
import sfeir.ynshb.exception.ResourceNotFoundException;
import sfeir.ynshb.mapper.TodoMapper;
import sfeir.ynshb.model.Todo;
import sfeir.ynshb.repository.TodoRepository;
import sfeir.ynshb.specification.SearchCriteria;
import sfeir.ynshb.specification.TodoSpecificationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    public List<TodoDto> getAll() {
        return todoRepository.findAll().stream().map(todoMapper::toDto).toList();
    }

    public TodoDto getById(Long id) {
        return todoMapper.toDto(getEntityById(id));
    }

    private Todo getEntityById(Long id) {
        return todoRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Todo with id: %d not exist", id)));
    }

    public TodoDto add(TodoDto todoToAdd) {
        Todo entity = todoMapper.toEntity(todoToAdd);
        return todoMapper.toDto(todoRepository.save(entity));
    }

    public TodoDto updateGlobally(Long id, TodoDto newDto) {
        Todo existingDto = getEntityById(id);
        todoMapper.updateGlobally(existingDto, newDto);
        return todoMapper.toDto(todoRepository.save(existingDto));
    }

    public TodoDto updatePartially(Long id, TodoDto newPartDto) {
        Todo existingDto = getEntityById(id);
        todoMapper.updatePartially(existingDto, newPartDto);
        return todoMapper.toDto(todoRepository.save(existingDto));
    }

    public void delete(Long id) {
        Todo dbTodo = getEntityById(id);
        todoRepository.delete(dbTodo);
    }

    public void deleteAll() {
        todoRepository.deleteAll();
    }

    public List<TodoDto> searchTodos(List<SearchCriteria> criteriaList) {
        if (Objects.isNull(criteriaList) || criteriaList.isEmpty()) {
            return getAll();
        }

        TodoSpecificationBuilder builder = new TodoSpecificationBuilder(new ArrayList<>());

        for (SearchCriteria criteria : criteriaList) {
            builder.with(criteria.getKey(), criteria.getOperation(), criteria.getValue());
        }

        Specification<Todo> spec = builder.build();
        return todoRepository.findAll(spec).stream().map(todoMapper::toDto).toList();
    }

}
