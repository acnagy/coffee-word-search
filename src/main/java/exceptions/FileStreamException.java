package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="there was an issue with the file input stream")
public class FileStreamException extends RuntimeException {

    public String readWriteError() {
        return "can't create inputstream";
    }

}

