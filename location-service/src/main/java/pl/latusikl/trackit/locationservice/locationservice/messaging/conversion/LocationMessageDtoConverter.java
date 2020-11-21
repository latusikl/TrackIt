package pl.latusikl.trackit.locationservice.locationservice.messaging.conversion;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.location.LocationMessageDto;
import pl.latusikl.trackit.locationservice.locationservice.messaging.services.LocationParser;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.LocationEntity;

@Component
@RequiredArgsConstructor
public class LocationMessageDtoConverter {

	private final GeometryFactory geometryFactory;
	private final LocationParser locationParser;

	public LocationEntity convert(final LocationMessageDto locationMessageDto) {
		return LocationEntity.builder()
							 .device_id(locationMessageDto.getId())
							 .dateTimeStart(locationMessageDto.getDateTime())
							 .location(convertCoordinates(locationMessageDto))
							 .build();
	}

	private Point convertCoordinates(final LocationMessageDto locationMessageDto) {
		return geometryFactory.createPoint(
				new Coordinate(locationParser.parseDegreesDecimalMinutesToDecimalDegrees(locationMessageDto.getLongitude()),
							   locationParser.parseDegreesDecimalMinutesToDecimalDegrees(locationMessageDto.getLatitude())));
	}

}
