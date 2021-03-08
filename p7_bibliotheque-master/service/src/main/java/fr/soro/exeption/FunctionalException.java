package fr.soro.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FunctionalException extends RuntimeException {

    private String message;

    public FunctionalException(String message) {
        this.message = message;
    }
}
