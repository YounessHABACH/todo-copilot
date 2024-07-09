package sfeir.ynshb.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TodoStatusEnum {
    CREATED("Created"),
    IN_PROGRESS("In Progress"),
    CLOSED("Closed"),
    ARCHIVED("Archived");

    private final String status;
}
