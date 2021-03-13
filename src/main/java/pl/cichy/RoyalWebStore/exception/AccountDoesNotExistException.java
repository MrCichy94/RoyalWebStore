package pl.cichy.RoyalWebStore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

public class AccountDoesNotExistException extends ResponseStatusException {

    public AccountDoesNotExistException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
        LOGGER.warn("Account does not exists!");
    }
}
