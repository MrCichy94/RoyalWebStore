package pl.cichy.RoyalWebStore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

public class ProductNotFoundException extends ResponseStatusException {

    public ProductNotFoundException(HttpStatus status, String reason, int productId) {
        super(status, reason);
        LOGGER.warn("No product found with id: " + productId);
    }
}

