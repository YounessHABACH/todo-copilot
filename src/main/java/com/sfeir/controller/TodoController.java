package com.sfeir.controller;


import com.sfeir.dto.TodoDto;
import com.sfeir.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<List<TodoDto>> getAll() {
        return ResponseEntity.ok(todoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getById(@PathVariable("id") Long id) throws RuntimeException {
        return ResponseEntity.ok(todoService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TodoDto> add(@Valid @RequestBody TodoDto todoToAdd) {
        return new ResponseEntity<>(todoService.add(todoToAdd), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateGlobal(@PathVariable("id") Long id, @Valid @RequestBody TodoDto todoToUpdate) {
        return ResponseEntity.ok(todoService.updateGlobal(id, todoToUpdate));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoDto> updatePart(@PathVariable("id") Long id, @Valid @RequestBody TodoDto todoToUpdate) {
        return ResponseEntity.ok(todoService.updatePart(id, todoToUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        todoService.delete(id);
        return ResponseEntity.ok().build();
    }

}
