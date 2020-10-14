package pl.latusikl.trackit.locationservice.locationservice.messaging.dto.location;

import lombok.Value;

@Value
public class CoordinatesDto {
	CardinalDirection direction;
	int degrees;
	double decimalMinutes;
}
