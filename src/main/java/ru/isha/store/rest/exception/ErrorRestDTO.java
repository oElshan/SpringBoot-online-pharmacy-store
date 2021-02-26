package ru.isha.store.rest.exception;

public class ErrorRestDTO {
    private final String message;
    private final String description;
    private final String entityName;

    public ErrorRestDTO(String message, String description, String entityName) {
        this.message = message;
        this.description = description;
        this.entityName = entityName;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public String getEntityName() {
        return entityName;
    }
}
