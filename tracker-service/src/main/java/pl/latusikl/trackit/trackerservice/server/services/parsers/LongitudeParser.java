package pl.latusikl.trackit.trackerservice.server.services.parsers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.messaging.dto.location.CoordinatesDto;
import pl.latusikl.trackit.trackerservice.server.constatns.LocationPacketConstants;

@Component
@RequiredArgsConstructor
public class LongitudeParser extends AbstractLocationParser {

	private static final int LONGITUDE_LENGTH = 3;
	private final LocationPacketConstants locationPacketConstants;

	@Override
	public CoordinatesDto parse(final String[] splitMessage) {
		return super.parse(splitMessage, locationPacketConstants.getLongitudePosition(),
						   locationPacketConstants.getLongitudeDirection(), LONGITUDE_LENGTH);
	}
}
