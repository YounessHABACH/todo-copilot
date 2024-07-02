package com.sfeir.service;

import com.sfeir.exception.ResourceNotFoundException;
import com.sfeir.model.Todo;
import com.sfeir.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    
    private final TodoRepository todoRepository;

    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    public Todo getById(Long id) {
        return todoRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Todo with id: %d not exist", id)));
    }

    public Todo add(Todo todoToAdd) {
        return todoRepository.save(todoToAdd);
    }

    public Todo update(Long id, Todo todoToUpdate) {
        Todo dbTodo = getById(id);
        dbTodo.setTitle(todoToUpdate.getTitle());
        dbTodo.setDescription(todoToUpdate.getDescription());
        dbTodo.setStatus(todoToUpdate.getStatus());
        return todoRepository.save(dbTodo);
    }

    public void delete(Long id) {
        Todo dbTodo = getById(id);
        todoRepository.delete(dbTodo);
    }
}
