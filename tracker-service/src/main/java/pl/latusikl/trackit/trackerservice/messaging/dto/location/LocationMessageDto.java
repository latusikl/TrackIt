package pl.latusikl.trackit.trackerservice.messaging.dto.location;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class LocationMessageDto {

	private String id;
	private LocalDateTime dateTime;
	private boolean gpsConnectionStatus;
	private CoordinatesDto latitude;
	private CoordinatesDto longitude;
}
