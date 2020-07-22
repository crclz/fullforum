package crclz.fullforum.errhand;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ForbidException extends ResponseStatusException {
    public ForbidException() {
        super(HttpStatus.FORBIDDEN, "No permission for the action");
    }

    public ForbidException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
