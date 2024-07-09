package sfeir.ynshb.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import sfeir.ynshb.dto.TodoDto;
import sfeir.ynshb.model.Todo;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    TodoDto toDto(Todo todo);

    Todo toEntity(TodoDto todoDto);

    void updateGlobally(@MappingTarget Todo todo, TodoDto todoDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePartially(@MappingTarget Todo todo, TodoDto todoDto);
}
