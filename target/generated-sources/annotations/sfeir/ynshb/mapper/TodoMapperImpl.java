package sfeir.ynshb.mapper;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;
import sfeir.ynshb.dto.TodoDto;
import sfeir.ynshb.dto.TodoDto.TodoDtoBuilder;
import sfeir.ynshb.model.Todo;
import sfeir.ynshb.model.Todo.TodoBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-09T13:48:24+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class TodoMapperImpl implements TodoMapper {

    private final DatatypeFactory datatypeFactory;

    public TodoMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public TodoDto toDto(Todo todo) {
        if ( todo == null ) {
            return null;
        }

        TodoDtoBuilder todoDto = TodoDto.builder();

        todoDto.id( todo.getId() );
        todoDto.title( todo.getTitle() );
        todoDto.description( todo.getDescription() );
        todoDto.status( todo.getStatus() );
        todoDto.createdAt( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( todo.getCreatedAt() ) ) );
        todoDto.updatedAt( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( todo.getUpdatedAt() ) ) );

        return todoDto.build();
    }

    @Override
    public Todo toEntity(TodoDto todoDto) {
        if ( todoDto == null ) {
            return null;
        }

        TodoBuilder todo = Todo.builder();

        todo.id( todoDto.getId() );
        todo.title( todoDto.getTitle() );
        todo.description( todoDto.getDescription() );
        todo.status( todoDto.getStatus() );
        todo.createdAt( xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( todoDto.getCreatedAt() ) ) );
        todo.updatedAt( xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( todoDto.getUpdatedAt() ) ) );

        return todo.build();
    }

    @Override
    public void updateGlobally(Todo todo, TodoDto todoDto) {
        if ( todoDto == null ) {
            return;
        }

        todo.setId( todoDto.getId() );
        todo.setTitle( todoDto.getTitle() );
        todo.setDescription( todoDto.getDescription() );
        todo.setStatus( todoDto.getStatus() );
        todo.setCreatedAt( xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( todoDto.getCreatedAt() ) ) );
        todo.setUpdatedAt( xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( todoDto.getUpdatedAt() ) ) );
    }

    @Override
    public void updatePartially(Todo todo, TodoDto todoDto) {
        if ( todoDto == null ) {
            return;
        }

        if ( todoDto.getId() != null ) {
            todo.setId( todoDto.getId() );
        }
        if ( todoDto.getTitle() != null ) {
            todo.setTitle( todoDto.getTitle() );
        }
        if ( todoDto.getDescription() != null ) {
            todo.setDescription( todoDto.getDescription() );
        }
        if ( todoDto.getStatus() != null ) {
            todo.setStatus( todoDto.getStatus() );
        }
        if ( todoDto.getCreatedAt() != null ) {
            todo.setCreatedAt( xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( todoDto.getCreatedAt() ) ) );
        }
        if ( todoDto.getUpdatedAt() != null ) {
            todo.setUpdatedAt( xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( todoDto.getUpdatedAt() ) ) );
        }
    }

    private static LocalDateTime xmlGregorianCalendarToLocalDateTime( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        if ( xcal.getYear() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMonth() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getDay() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getHour() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMinute() != DatatypeConstants.FIELD_UNDEFINED
        ) {
            if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED
                && xcal.getMillisecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    Duration.ofMillis( xcal.getMillisecond() ).getNano()
                );
            }
            else if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute()
                );
            }
        }
        return null;
    }

    private static LocalDate xmlGregorianCalendarToLocalDate( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        return LocalDate.of( xcal.getYear(), xcal.getMonth(), xcal.getDay() );
    }

    private XMLGregorianCalendar localDateTimeToXmlGregorianCalendar( LocalDateTime localDateTime ) {
        if ( localDateTime == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendar(
            localDateTime.getYear(),
            localDateTime.getMonthValue(),
            localDateTime.getDayOfMonth(),
            localDateTime.getHour(),
            localDateTime.getMinute(),
            localDateTime.getSecond(),
            localDateTime.get( ChronoField.MILLI_OF_SECOND ),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private XMLGregorianCalendar localDateToXmlGregorianCalendar( LocalDate localDate ) {
        if ( localDate == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendarDate(
            localDate.getYear(),
            localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            DatatypeConstants.FIELD_UNDEFINED );
    }
}
