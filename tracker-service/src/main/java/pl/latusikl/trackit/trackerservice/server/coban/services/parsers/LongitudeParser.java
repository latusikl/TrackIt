package pl.latusikl.trackit.trackerservice.server.coban.services.parsers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.server.coban.constatns.LocationPacketConstants;
import pl.latusikl.trackit.trackerservice.server.common.dto.CoordinatesDto;

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
