package sfeir.ynshb.utilities;

import sfeir.ynshb.dto.TodoDto;
import sfeir.ynshb.model.Todo;
import sfeir.ynshb.model.TodoStatusEnum;
import sfeir.ynshb.specification.SearchCriteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TodoUtilities {

    // get random long between 0 and 100
    public static Long getRandom() {
        return new Random().nextLong();
    }

    // get empty list of todos
    public static List<Todo> getEmptyTodos() {
        return Collections.emptyList();
    }

    // get empty list of todoDtos
    public static List<TodoDto> getEmptyTodoDtos() {
        return Collections.emptyList();
    }

    // generate a list of todos using lombok builder in Todo
    public static List<Todo> getAllTodos() {
        List<Todo> todos = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            todos.add(Todo.builder()
                    .id((long) i)
                    .title("Todo " + i)
                    .description("Description " + i)
                    .status(TodoStatusEnum.CREATED)
                    .build());
        }
        return todos;
    }

    // genrate list of TodoDto
    public static List<TodoDto> getAllTodoDtos() {
        List<TodoDto> todoDtos = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            todoDtos.add(TodoDto.builder()
                    .id((long) i)
                    .title("Todo " + i)
                    .description("Description " + i)
                    .status(TodoStatusEnum.CREATED)
                    .build());
        }
        return todoDtos;
    }

    // generate a todo using lombok builder in Todo
    public static Todo getTodo() {
        return Todo.builder()
                .id(1L)
                .title("Todo")
                .description("Description")
                .status(TodoStatusEnum.CREATED)
                .build();
    }

    // generate a todoDto using lombok builder in TodoDto
    public static TodoDto getTodoDto() {
        return TodoDto.builder()
                .id(1L)
                .title("Todo")
                .description("Description")
                .status(TodoStatusEnum.CREATED)
                .build();
    }

    // generate list of search criterias with 3 enteries using lombok builder in SearchCriteria
    public static List<SearchCriteria> getSearchCriterias() {
        List<SearchCriteria> searchCriterias = new ArrayList<>();
        searchCriterias.add(SearchCriteria.builder()
                .key("title")
                .operation(":")
                .value("Todo")
                .build());
        searchCriterias.add(SearchCriteria.builder()
                .key("description")
                .operation(":")
                .value("Description")
                .build());
        searchCriterias.add(SearchCriteria.builder()
                .key("status")
                .operation(":")
                .value("CREATED")
                .build());
        return searchCriterias;
    }

}
