package cz.my.snemovna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * The main application class. Runs Spring boot.
 */
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SnemovnaApplication {

	/**
	 * The main application method.
	 */
	public static void main(String[] args) {
		SpringApplication.run(SnemovnaApplication.class, args);
	}
}
