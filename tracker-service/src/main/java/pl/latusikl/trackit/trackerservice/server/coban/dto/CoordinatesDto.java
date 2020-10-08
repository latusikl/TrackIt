package pl.latusikl.trackit.trackerservice.server.coban.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoordinatesDto {
	CardinalDirection direction;
	int degrees;
	double decimalMinutes;
}
