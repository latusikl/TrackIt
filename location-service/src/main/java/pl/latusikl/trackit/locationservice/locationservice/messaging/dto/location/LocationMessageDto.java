package pl.latusikl.trackit.locationservice.locationservice.messaging.dto.location;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class LocationMessageDto {

	private String id;
	private LocalDateTime dateTime;
	private boolean gpsConnectionStatus;
	private CoordinatesDto latitude;
	private CoordinatesDto longitude;
}
