package pl.latusikl.trackit.locationservice.locationservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.latusikl.trackit.locationservice.locationservice.messaging.OutboundProcessor;

@Configuration
@EnableBinding(OutboundProcessor.class)
public class LocationServiceConfig {

	@Bean
	ObjectMapper objectMapper() {
		final var objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objectMapper;
	}
}
