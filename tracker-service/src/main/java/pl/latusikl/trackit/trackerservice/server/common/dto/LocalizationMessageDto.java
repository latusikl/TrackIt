package pl.latusikl.trackit.trackerservice.server.common.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LocalizationMessageDto {

	private String imei;
	private LocalDateTime dateTime;
	private boolean gpsConnectionStatus;
	private CoordinatesDto latitude;
	private CoordinatesDto longitude;
}
