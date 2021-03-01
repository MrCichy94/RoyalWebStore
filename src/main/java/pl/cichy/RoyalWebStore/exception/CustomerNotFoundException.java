package pl.cichy.RoyalWebStore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

public class CustomerNotFoundException extends ResponseStatusException {

    public CustomerNotFoundException(HttpStatus status, String reason, int customerId) {
        super(status, reason);
        LOGGER.warn("No customer found with id: " + customerId);
    }
}
