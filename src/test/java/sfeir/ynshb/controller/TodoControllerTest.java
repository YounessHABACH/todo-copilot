package sfeir.ynshb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sfeir.ynshb.dto.TodoDto;
import sfeir.ynshb.exception.ResourceNotFoundException;
import sfeir.ynshb.exception.TodoExceptionHandling;
import sfeir.ynshb.model.Todo;
import sfeir.ynshb.service.TodoService;
import sfeir.ynshb.utilities.TodoUtilities;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TodoControllerTest {

    private MockMvc mockMvc;
    private final String API_END_POINT_BASE = "/api/v1/todos";
    private final String API_END_POINT_ID = "/api/v1/todos/{id}";
    private final String EXCEPTION_MESSAGE = "Failed to add todo";

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    // set up for each and add controller advice
    @BeforeEach
    void set_up() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(todoController)
                .setControllerAdvice(new TodoExceptionHandling())
                .build();
    }

    @Test
    void should_return_all_todos_successfully() throws Exception {
        List<TodoDto> allTodoDtos = TodoUtilities.getAllTodoDtos();
        when(todoService.getAll()).thenReturn(allTodoDtos);

        mockMvc.perform(get(API_END_POINT_BASE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(allTodoDtos.size())))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Todo 1"));
    }

    // return all todos as empty list
    @Test
    void should_return_all_todos_as_empty_list() throws Exception {
        List<TodoDto> emptyTodoDtos = TodoUtilities.getEmptyTodoDtos();
        when(todoService.getAll()).thenReturn(emptyTodoDtos);

        mockMvc.perform(get(API_END_POINT_BASE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(emptyTodoDtos.size())));
    }

    // return todo by id
    @Test
    void should_return_todo_by_id() throws Exception {
        TodoDto todoDto = TodoUtilities.getTodoDto();
        when(todoService.getById(anyLong())).thenReturn(todoDto);

        mockMvc.perform(get(API_END_POINT_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoDto.getId()))
                .andExpect(jsonPath("$.title").value(todoDto.getTitle()));
    }

    // return todo by id not found
    @Test
    void should_return_todo_by_id_not_found() throws Exception {
        when(todoService.getById(anyLong())).thenThrow(new ResourceNotFoundException(EXCEPTION_MESSAGE));

        mockMvc.perform(get(API_END_POINT_ID, 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(EXCEPTION_MESSAGE));
    }

    // add todo and add content body and type
    @Test
    void should_add_todo_successfully() throws Exception {
        TodoDto todoDto = TodoUtilities.getTodoDto();
        when(todoService.add(any(TodoDto.class))).thenReturn(todoDto);

        mockMvc.perform(post(API_END_POINT_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(todoDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(todoDto.getId()))
                .andExpect(jsonPath("$.title").value(todoDto.getTitle()));
    }

    // add todo failed
    @Test
    void should_add_todo_failed() throws Exception {
        TodoDto todoDto = TodoUtilities.getTodoDto();
        when(todoService.add(any(TodoDto.class))).thenThrow(new ResourceNotFoundException(EXCEPTION_MESSAGE));

        mockMvc.perform(post(API_END_POINT_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(todoDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(EXCEPTION_MESSAGE));
    }

    // update todo globally put method success
    @Test
    void should_update_todo_globally_successfully() throws Exception {
        TodoDto todoDto = TodoUtilities.getTodoDto();
        when(todoService.updateGlobally(anyLong(), any(TodoDto.class))).thenReturn(todoDto);

        mockMvc.perform(put(API_END_POINT_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(todoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoDto.getId()))
                .andExpect(jsonPath("$.title").value(todoDto.getTitle()));
    }

    // update todo globally put method failed
    @Test
    void should_update_todo_globally_failed() throws Exception {
        TodoDto todoDto = TodoUtilities.getTodoDto();
        when(todoService.updateGlobally(anyLong(), any(TodoDto.class))).thenThrow(new ResourceNotFoundException(EXCEPTION_MESSAGE));

        mockMvc.perform(put(API_END_POINT_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(todoDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(EXCEPTION_MESSAGE));
    }

    // update todo partially http patch method success
    @Test
    void should_update_todo_partially_successfully() throws Exception {
        TodoDto todoDto = TodoUtilities.getTodoDto();
        when(todoService.updatePartially(anyLong(), any(TodoDto.class))).thenReturn(todoDto);

        mockMvc.perform(patch(API_END_POINT_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(todoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoDto.getId()))
                .andExpect(jsonPath("$.title").value(todoDto.getTitle()));
    }

    // update todo partially http patch method failed
    @Test
    void should_update_todo_partially_failed() throws Exception {
        TodoDto todoDto = TodoUtilities.getTodoDto();
        when(todoService.updatePartially(anyLong(), any(TodoDto.class))).thenThrow(new ResourceNotFoundException(EXCEPTION_MESSAGE));

        mockMvc.perform(patch(API_END_POINT_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(todoDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(EXCEPTION_MESSAGE));
    }

    // delete todo by id
    @Test
    void should_delete_todo_by_id() throws Exception {
        Mockito.doNothing().when(todoService).delete(1L);

        mockMvc.perform(delete(API_END_POINT_ID, 1L))
                .andExpect(status().isOk());
    }

    // delete todo by id failed
    @Test
    void should_delete_todo_by_id_failed() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException(EXCEPTION_MESSAGE)).when(todoService).delete(1L);

        mockMvc.perform(delete(API_END_POINT_ID, 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(EXCEPTION_MESSAGE));
    }

    // search todos
    @Test
    void should_search_todos() throws Exception {
        List<TodoDto> todoDtos = TodoUtilities.getAllTodoDtos();
        when(todoService.searchTodos(anyList())).thenReturn(todoDtos);

        mockMvc.perform(post(API_END_POINT_BASE + "/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(TodoUtilities.getSearchCriterias())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(todoDtos.size())))
                .andExpect(jsonPath("$[0].id").value(todoDtos.get(0).getId()))
                .andExpect(jsonPath("$[0].title").value(todoDtos.get(0).getTitle()));
    }

    // search todos as empty list
    @Test
    void should_search_todos_as_empty_list() throws Exception {
        List<TodoDto> todoDtos = TodoUtilities.getAllTodoDtos();
        when(todoService.searchTodos(anyList())).thenReturn(todoDtos);

        mockMvc.perform(post(API_END_POINT_BASE + "/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(Collections.emptyList())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(todoDtos.size())));
    }

}
