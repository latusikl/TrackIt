package pl.latusikl.trackit.trackerservice.messaging.dto.location;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CoordinatesDto {
	CardinalDirection direction;
	int degrees;
	double decimalMinutes;
}
