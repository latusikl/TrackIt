package pl.latusikl.trackit.locationservice.locationservice.web.service.conversion;

import org.springframework.stereotype.Component;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.location.CardinalDirection;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.location.CoordinatesDto;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.location.LocationMessageDto;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.LocationEntity;

@Component
public class LocationMessageDtoConverter {

	private static final int MINUTES_PER_DEGREE = 60;

	public LocationEntity convert(final LocationMessageDto locationMessageDto) {
		return LocationEntity.builder()
							 .device_id(locationMessageDto.getId())
							 .dateTimeStart(locationMessageDto.getDateTime())
							 .longitude(parseDegreesDecimalMinutesToDecimalDegrees(locationMessageDto.getLongitude()))
							 .latitude(parseDegreesDecimalMinutesToDecimalDegrees(locationMessageDto.getLatitude()))
							 .build();
	}

	private Double parseDegreesDecimalMinutesToDecimalDegrees(final CoordinatesDto coordinatesDto) {
		final double value = coordinatesDto.getDegrees() + coordinatesDto.getDecimalMinutes() / MINUTES_PER_DEGREE;

		if (coordinatesDto.getDirection() == CardinalDirection.SOUTH || coordinatesDto.getDirection() == CardinalDirection.WEST) {
			return -value;
		}
		return value;
	}
}
