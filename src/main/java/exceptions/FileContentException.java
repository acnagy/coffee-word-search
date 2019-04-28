package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="there was a read/write issue with the file")
public class FileContentException extends RuntimeException {

    public String readWriteError() {
        return "read write error";
    }
}
