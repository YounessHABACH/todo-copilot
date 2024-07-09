package sfeir.ynshb.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class ErrorDto {
    private String message;
    private List<String> messageDetails;
}
