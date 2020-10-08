package pl.latusikl.trackit.trackerservice.server.coban.services.parsers;

import pl.latusikl.trackit.trackerservice.server.coban.dto.CardinalDirection;
import pl.latusikl.trackit.trackerservice.server.coban.dto.CoordinatesDto;
import pl.latusikl.trackit.trackerservice.server.coban.excpetions.MessageParsingException;

public abstract class AbstractLocationParser implements LocationParser {

	protected CoordinatesDto parse(final String[] splitMessage, final int coordinateIndex, final int directionSymbolIndex,
			final int degreesSigns) {
		final String longitude = splitMessage[coordinateIndex];
		final String directionSymbol = splitMessage[directionSymbolIndex];

		return CoordinatesDto.builder()
							 .direction(CardinalDirection.fromSymbol(directionSymbol)
														 .orElseThrow(this::directionSymbolException))
							 .degrees(Integer.parseInt(longitude.substring(0, degreesSigns)))
							 .decimalMinutes(Double.parseDouble(longitude.substring(degreesSigns)))
							 .build();
	}

	private MessageParsingException directionSymbolException() {
		return new MessageParsingException("Unable to read for direction one of coordinates ", LongitudeParser.class);
	}
}
