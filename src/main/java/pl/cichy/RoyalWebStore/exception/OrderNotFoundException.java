package pl.cichy.RoyalWebStore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

public class OrderNotFoundException extends ResponseStatusException {

    public OrderNotFoundException(HttpStatus status, String reason, Throwable cause, int orderId) {
        super(status, reason, cause);
        LOGGER.warn("No order found with id: " + orderId);
    }
}
