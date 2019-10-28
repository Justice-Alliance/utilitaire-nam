package ca.qc.inspq.nam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import ca.qc.inspq.nam.api.UtilitaireNamApiApplication;

@SpringBootApplication
@Import(UtilitaireNamApiApplication.class)
public class Application {
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
