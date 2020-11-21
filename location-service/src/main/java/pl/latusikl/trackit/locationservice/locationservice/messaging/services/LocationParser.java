package pl.latusikl.trackit.locationservice.locationservice.messaging.services;

import org.springframework.stereotype.Component;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.location.CardinalDirection;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.location.CoordinatesDto;

@Component
public class LocationParser {

	private static final int MINUTES_PER_DEGREE = 60;

	public Double parseDegreesDecimalMinutesToDecimalDegrees(final CoordinatesDto coordinatesDto) {
		final double value = coordinatesDto.getDegrees() + coordinatesDto.getDecimalMinutes() / MINUTES_PER_DEGREE;

		if (coordinatesDto.getDirection() == CardinalDirection.SOUTH || coordinatesDto.getDirection() == CardinalDirection.WEST) {
			return -value;
		}
		return value;
	}

}
