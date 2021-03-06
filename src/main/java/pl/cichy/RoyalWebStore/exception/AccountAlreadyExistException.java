package pl.cichy.RoyalWebStore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

public class AccountAlreadyExistException extends ResponseStatusException {

    public AccountAlreadyExistException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
        LOGGER.warn("Account with this address already exist!");
    }
}

