package pl.latusikl.trackit.trackerservice.server.services.parsers;

import pl.latusikl.trackit.trackerservice.messaging.dto.location.CoordinatesDto;

public interface LocationParser {
	CoordinatesDto parse(final String[] splitMessage);
}
