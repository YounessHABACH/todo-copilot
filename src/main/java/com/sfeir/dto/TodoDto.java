package com.sfeir.dto;

import com.sfeir.model.TodoEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TodoDto {
    private Long id;
    private String title;
    private String description;
    private TodoEnum status;
}
