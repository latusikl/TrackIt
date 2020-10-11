package pl.latusikl.trackit.trackerservice.server.coban.services.parsers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.server.coban.constatns.LocationPacketConstants;
import pl.latusikl.trackit.trackerservice.server.coban.dto.CoordinatesDto;

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
