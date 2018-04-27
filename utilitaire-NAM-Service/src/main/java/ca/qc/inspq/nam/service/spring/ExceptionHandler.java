package ca.qc.inspq.nam.service.spring;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Le NAM est invalide")
	@org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
	public void handleIllegalArgumentException() {
		// Nothing to do
	}
}