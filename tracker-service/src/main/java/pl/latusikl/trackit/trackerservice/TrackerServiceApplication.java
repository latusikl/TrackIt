package pl.latusikl.trackit.trackerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan("pl.latusikl.trackit.trackerservice.properties")
@EnableScheduling
public class TrackerServiceApplication {
	public static void main(final String[] args) {
		SpringApplication.run(TrackerServiceApplication.class, args);
	}
}

