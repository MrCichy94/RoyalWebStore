package pl.cichy.RoyalWebStore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

public class CopyNotFoundException extends ResponseStatusException {

    public CopyNotFoundException(HttpStatus status, String reason, Throwable cause, int copyId) {
        super(status, reason, cause);
        LOGGER.warn("No copy found with id: " + copyId);
    }

}
