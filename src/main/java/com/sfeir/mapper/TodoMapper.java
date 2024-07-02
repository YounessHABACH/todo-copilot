package com.sfeir.mapper;

import com.sfeir.dto.TodoDto;
import com.sfeir.model.Todo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    TodoMapper mapper = Mappers.getMapper(TodoMapper.class);

    Todo toEntity(TodoDto dto);

    TodoDto toDto(Todo todo);

    void globalUpdate(@MappingTarget Todo todo, TodoDto todoDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Todo todo, TodoDto todoDto);
}
