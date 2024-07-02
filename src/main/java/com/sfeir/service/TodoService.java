package com.sfeir.service;

import com.sfeir.dto.TodoDto;
import com.sfeir.exception.ResourceNotFoundException;
import com.sfeir.mapper.TodoMapper;
import com.sfeir.model.Todo;
import com.sfeir.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    public List<TodoDto> getAll() {
        return todoRepository.findAll().stream().map(todoMapper::toDto).toList();
    }

    public TodoDto getById(Long id) {
        return todoRepository
                .findById(id)
                .map(todoMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Todo with id: %d not exist", id)));
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

    public TodoDto updateGlobal(Long id, TodoDto newDto) {
        Todo existingDto = getEntityById(id);
        todoMapper.globalUpdate(existingDto, newDto);
        return todoMapper.toDto(todoRepository.save(existingDto));
    }

    public TodoDto updatePart(Long id, TodoDto newPartDto) {
        Todo existingDto = getEntityById(id);
        todoMapper.partialUpdate(existingDto, newPartDto);
        return todoMapper.toDto(todoRepository.save(existingDto));
    }

    public void delete(Long id) {
        Todo dbTodo = getEntityById(id);
        todoRepository.delete(dbTodo);
    }


}
