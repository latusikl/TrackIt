package pl.latusikl.trackit.locationservice.locationservice.messaging.dto.location;

import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Optional;

@AllArgsConstructor
public enum CardinalDirection implements Serializable {
	NORTH(CardinalDirection.SHORT_NORTH), SOUTH(
			CardinalDirection.SHORT_SOUTH), EAST(
			CardinalDirection.SHORT_EAST), WEST(
			CardinalDirection.SHORT_WEST);

	private static final String SHORT_NORTH = "N";
	private static final String SHORT_SOUTH = "S";
	private static final String SHORT_WEST = "W";
	private static final String SHORT_EAST = "E";
	private final String symbol;

	public static Optional<CardinalDirection> fromSymbol(final String symbol) {
		switch (symbol) {
			case SHORT_NORTH:
				return Optional.of(NORTH);
			case SHORT_SOUTH:
				return Optional.of(SOUTH);
			case SHORT_EAST:
				return Optional.of(EAST);
			case SHORT_WEST:
				return Optional.of(WEST);
			default:
				return Optional.empty();
		}
	}

	public String toSymbol() {
		return this.symbol;
	}
}
