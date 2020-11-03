package pl.latusikl.trackit.locationservice.locationservice.web.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidGenerator {
	public String randomUuid() {
		return UUID.randomUUID()
				   .toString();
	}
}
