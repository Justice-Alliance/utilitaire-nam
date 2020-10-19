package ca.qc.inspq.nam.service.spring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

	@GetMapping
	public ResponseEntity<String> baseUrl() {
		return new ResponseEntity<String>("Utilitaire NAM", HttpStatus.OK);
	}
}
