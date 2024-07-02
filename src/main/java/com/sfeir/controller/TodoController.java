package com.sfeir.controller;


import com.sfeir.model.Todo;
import com.sfeir.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public List<Todo> getAll() {
        return todoService.getAll();
    }

    @GetMapping("/{id}")
    public Todo getById(@PathVariable("id") Long id) throws RuntimeException {
        return todoService.getById(id);
    }

    @PostMapping
    public Todo add(@Valid @RequestBody Todo todoToAdd) {
        return todoService.add(todoToAdd);
    }

    @PutMapping("/{id}")
    public Todo update(@PathVariable("id") Long id,@Valid @RequestBody Todo todoToUpdate) {
        return todoService.update(id, todoToUpdate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        todoService.delete(id);
    }

}
