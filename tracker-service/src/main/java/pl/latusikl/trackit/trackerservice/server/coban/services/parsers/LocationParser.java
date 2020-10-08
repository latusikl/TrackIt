package pl.latusikl.trackit.trackerservice.server.coban.services.parsers;

import pl.latusikl.trackit.trackerservice.server.coban.dto.CoordinatesDto;

public interface LocationParser {
	CoordinatesDto parse(final String[] splitMessage);
}
