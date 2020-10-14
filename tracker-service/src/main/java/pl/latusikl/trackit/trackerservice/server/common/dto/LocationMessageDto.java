package pl.latusikl.trackit.trackerservice.server.common.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LocationMessageDto {

	private String id;
	private LocalDateTime dateTime;
	private boolean gpsConnectionStatus;
	private CoordinatesDto latitude;
	private CoordinatesDto longitude;
}
