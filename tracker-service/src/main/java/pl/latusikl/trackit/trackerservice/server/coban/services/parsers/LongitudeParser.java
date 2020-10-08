package pl.latusikl.trackit.trackerservice.server.coban.services.parsers;

import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.server.coban.CobanConstants;
import pl.latusikl.trackit.trackerservice.server.coban.dto.CoordinatesDto;

@Component
public class LongitudeParser extends AbstractLocationParser {

	private static final int LONGITUDE_LENGTH = 3;

	@Override
	public CoordinatesDto parse(final String[] splitMessage) {
		return super.parse(splitMessage, CobanConstants.LocationPacket.LONGITUDE_POSITION,
				CobanConstants.LocationPacket.LONGITUDE_DIRECTION, LONGITUDE_LENGTH);
	}
}
