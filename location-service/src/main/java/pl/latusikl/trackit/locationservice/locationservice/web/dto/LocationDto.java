package pl.latusikl.trackit.locationservice.locationservice.web.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class LocationDto {

	Double longitude;
	Double latitude;
	LocalDateTime positionTimestamp;
}
