package sfeir.ynshb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sfeir.ynshb.model.TodoStatusEnum;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TodoDto {
    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    private String description;
    @NotNull
    private TodoStatusEnum status;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
