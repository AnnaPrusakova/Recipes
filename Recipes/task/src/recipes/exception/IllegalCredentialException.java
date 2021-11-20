package recipes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IllegalCredentialException extends RuntimeException {
    public IllegalCredentialException(String message) {
        super(message);
    }
}
