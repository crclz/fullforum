package crclz.fullforum.errhand;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends ResponseStatusException {
    public ErrorCode code;

    public BadRequestException(ErrorCode code, String message) {
        super(HttpStatus.BAD_REQUEST, message);
        this.code = code;
    }
}
