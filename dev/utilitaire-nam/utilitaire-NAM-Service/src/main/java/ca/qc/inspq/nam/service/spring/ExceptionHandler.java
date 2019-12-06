package ca.qc.inspq.nam.service.spring;

import java.security.InvalidParameterException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandler {

    //    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Le NAM est invalide")
    //    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidParameterException.class)
    //    public void handleInvalidParameterException() {
    //        // Nothing to do
    //    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ InvalidParameterException.class })
    public ResponseEntity<Object> handleInvalidParameterException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                ex.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

}