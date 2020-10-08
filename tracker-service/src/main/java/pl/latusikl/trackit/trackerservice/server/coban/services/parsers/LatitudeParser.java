package pl.latusikl.trackit.trackerservice.server.coban.services.parsers;

import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.server.coban.dto.CoordinatesDto;

import static pl.latusikl.trackit.trackerservice.server.coban.CobanConstants.LocationPacket;

@Component
public class LatitudeParser extends AbstractLocationParser {

	private static final int LATITUDE_LENGTH = 2;

	@Override
	public CoordinatesDto parse(final String[] splitMessage) {
		return super.parse(splitMessage, LocationPacket.LATITUDE_POSITION, LocationPacket.LATITUDE_DIRECTION, LATITUDE_LENGTH);
	}
}
