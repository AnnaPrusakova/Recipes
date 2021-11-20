package recipes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class IllegalRequestException extends RuntimeException {

    public IllegalRequestException(String message) {
        super(message);
    }
}
