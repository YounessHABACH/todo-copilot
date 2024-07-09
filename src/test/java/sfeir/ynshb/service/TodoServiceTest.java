package sfeir.ynshb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;
import sfeir.ynshb.dto.TodoDto;
import sfeir.ynshb.exception.ResourceNotFoundException;
import sfeir.ynshb.mapper.TodoMapper;
import sfeir.ynshb.model.Todo;
import sfeir.ynshb.repository.TodoRepository;
import sfeir.ynshb.specification.SearchCriteria;
import sfeir.ynshb.utilities.TodoUtilities;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private TodoMapper todoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        todoMapper = TodoMapper.INSTANCE; // Assuming TodoMapper is a MapStruct mapper with an INSTANCE field
        ReflectionTestUtils.setField(todoService, "todoMapper", todoMapper);
    }

    // generate test for get all todos successfully
    @Test
    public void should_returns_all_todos_when_db_has_multiple_entries() {
        // Given
        List<Todo> todos = TodoUtilities.getAllTodos();

        // When
        when(todoRepository.findAll()).thenReturn(todos);

        // Then
        List<TodoDto> todosResult = todoService.getAll();
        assertEquals(todos.size(), todosResult.size());
    }

    // generate test for get all todos return empty list when db is empty
    @Test
    public void should_returns_empty_list_when_db_is_empty() {
        // Given
        // When
        when(todoRepository.findAll()).thenReturn(Collections.emptyList());

        // Then
        List<TodoDto> todosResult = todoService.getAll();
        assertTrue(todosResult.isEmpty());
    }

    // generate test for get a single todo by id successfully
    @Test
    public void should_returns_todo_when_id_exists() {
        // Given
        Todo todo = TodoUtilities.getTodo();

        // When
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        // Then
        TodoDto todoResult = todoService.getById(1L);
        assertEquals(todo.getId(), todoResult.getId());
    }

    // generate test for get a single by id that does not exist
    @Test
    public void should_throw_exception_when_id_does_not_exist() {
        // Given
        // When
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> todoService.getById(1L));
    }

    // generate test to add todo successfully
    @Test
    public void should_add_todo_successfully() {
        // Given
        TodoDto todoDto = TodoUtilities.getTodoDto();
        Todo todo = todoMapper.toEntity(todoDto);

        // When
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        // Then
        TodoDto todoResult = todoService.add(todoDto);
        assertEquals(todoDto.getId(), todoResult.getId());
    }

    // generate test to update globally successfully
    @Test
    public void should_update_todo_globally_successfully() {
        // Given
        TodoDto todoDto = TodoUtilities.getTodoDto();
        Todo todo = todoMapper.toEntity(todoDto);

        // When
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        // Then
        TodoDto todoResult = todoService.updateGlobally(1L, todoDto);
        assertEquals(todoDto.getId(), todoResult.getId());
    }

    // generate test to update globally with null title
    @Test
    public void should_throw_exception_when_updating_todo_globally_with_null_title() {
        // Given
        TodoDto todoDto = TodoUtilities.getTodoDto();
        todoDto.setTitle(null);

        // When
        // Then
        assertThrows(ResourceNotFoundException.class, () -> todoService.updateGlobally(1L, todoDto));
    }

    // generate test to update globally with id that does not exist
    @Test
    public void should_throw_exception_when_updating_todo_globally_with_id_that_does_not_exist() {
        // Given
        TodoDto todoDto = TodoUtilities.getTodoDto();

        // When
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> todoService.updateGlobally(1L, todoDto));
    }

    // generate test to update partially successfully
    @Test
    public void should_update_todo_partially_successfully() {
        // Given
        TodoDto todoDto = TodoUtilities.getTodoDto();
        Todo todo = todoMapper.toEntity(todoDto);

        // When
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        // Then
        TodoDto todoResult = todoService.updatePartially(1L, todoDto);
        assertEquals(todoDto.getId(), todoResult.getId());
    }

    // generate test to update partially with id that does not exist
    @Test
    public void should_throw_exception_when_updating_todo_partially_with_id_that_does_not_exist() {
        // Given
        TodoDto todoDto = TodoUtilities.getTodoDto();

        // When
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> todoService.updatePartially(1L, todoDto));
    }

    // generate test to delete todo successfully
    @Test
    public void should_delete_todo_successfully() {
        // Given
        Todo todo = TodoUtilities.getTodo();

        // When
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        doNothing().when(todoRepository).delete(todo);

        // Then
        assertDoesNotThrow(() -> todoService.delete(1L));
        verify(todoRepository, times(1)).delete(todo);
    }

    // generate test to delete todo with id that does not exist
    @Test
    public void should_throw_exception_when_deleting_todo_with_id_that_does_not_exist() {
        // Given
        // When
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> todoService.delete(1L));
    }

    // ggenerate delete all todos successfully
    @Test
    public void should_delete_all_todos_successfully() {
        // Given
        List<Todo> todos = TodoUtilities.getAllTodos();

        // When
        when(todoRepository.findAll()).thenReturn(todos);
        doNothing().when(todoRepository).deleteAll();

        // Then
        assertDoesNotThrow(() -> todoService.deleteAll());
        verify(todoRepository, times(1)).deleteAll();
    }

    // generate test to search todos successfully
    @Test
    public void should_search_todos_successfully() {
        // Given
        List<Todo> todos = TodoUtilities.getAllTodos();
        // use todoUtilities method to get criterias
        List<SearchCriteria> searchCriteriaList = TodoUtilities.getSearchCriterias();

        // When
        when(todoRepository.findAll(any(Specification.class))).thenReturn(todos);

        // Then
        List<TodoDto> todosResult = todoService.searchTodos(searchCriteriaList);
        assertEquals(todos.size(), todosResult.size());
    }

    // generate test to search todos with empty search criteria
    @Test
    public void should_search_todos_with_empty_search_criteria() {
        // Given
        List<Todo> todos = TodoUtilities.getAllTodos();
        List<SearchCriteria> searchCriteriaList = Collections.emptyList();

        // When
        when(todoRepository.findAll(any(Specification.class))).thenReturn(todos);
        when(todoRepository.findAll()).thenReturn(todos);

        // Then
        List<TodoDto> todosResult = todoService.searchTodos(searchCriteriaList);
        assertEquals(todos.size(), todosResult.size());
    }

    // generate test to search todos with null search criteria
    @Test
    public void should_search_todos_with_null_search_criteria() {
        // Given
        List<Todo> todos = TodoUtilities.getAllTodos();
        List<SearchCriteria> searchCriteriaList = null;

        // When
        when(todoRepository.findAll(any(Specification.class))).thenReturn(todos);
        when(todoRepository.findAll()).thenReturn(todos);

        // Then
        List<TodoDto> todosResult = todoService.searchTodos(searchCriteriaList);
        assertEquals(todos.size(), todosResult.size());
    }

}