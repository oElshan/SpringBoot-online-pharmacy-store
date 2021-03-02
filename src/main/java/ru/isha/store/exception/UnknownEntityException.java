package ru.isha.store.exception;

/**
 * Попытка сослаться на неизвестный товар.
 */
public class UnknownEntityException extends RuntimeException{
	private static final long serialVersionUID = 4827971686664741607L;
	private final String eventType;
	private final String entityType;

	public UnknownEntityException(String eventType, String entityType) {
		super();
		this.eventType = eventType;
		this.entityType = entityType;
	}


	public String getEventType() {
		return eventType;
	}

	public String getEntityType() {
		return entityType;
	}

}
