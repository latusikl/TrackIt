package pl.latusikl.trackit.trackerservice.server.services.parsers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.messaging.dto.location.CoordinatesDto;
import pl.latusikl.trackit.trackerservice.server.constatns.LocationPacketConstants;

@Component
@RequiredArgsConstructor
public class LatitudeParser extends AbstractLocationParser {

	private static final int LATITUDE_LENGTH = 2;
	private final LocationPacketConstants locationPacketConstants;
	@Override
	public CoordinatesDto parse(final String[] splitMessage) {
		return super.parse(splitMessage, locationPacketConstants.getLatitudePosition(), locationPacketConstants.getLatitudeDirection(), LATITUDE_LENGTH);
	}
}
