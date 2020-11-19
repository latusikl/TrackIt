package pl.latusikl.trackit.locationservice.locationservice.web.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class TimeUtcUtils {

	public LocalDateTime getUtcNow() {
		return LocalDateTime.now(ZoneId.of("UTC"));
	}

}
