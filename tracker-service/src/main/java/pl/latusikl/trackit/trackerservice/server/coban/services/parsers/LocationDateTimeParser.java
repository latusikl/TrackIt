package pl.latusikl.trackit.trackerservice.server.coban.services.parsers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.server.coban.constatns.LocationPacketConstants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class LocationDateTimeParser {

	private static final int YEAR_END_INDEX = 2;
	private static final int MONTH_END_INDEX = 4;
	private static final int DAY_END_INDEX = 6;
	private static final int HOUR_END_INDEX = 8;
	private static final int MINUTE_END_INDEX = 10;
	private static final int SECOND_END_INDEX = 12;

	private static final String YEAR_PREFIX = "20";

	private final LocationPacketConstants locationPacketConstants;

	public LocalDateTime extractDateAndTime(final String[] splitMessage) {
		final String rawDateAndTime = splitMessage[locationPacketConstants.getDateTimePosition()];
		final LocalDate localDate = LocalDate.of(extractYear(rawDateAndTime), extractMonth(rawDateAndTime),
												 extractDay(rawDateAndTime));
		final LocalTime localTime = LocalTime.of(extractHour(rawDateAndTime), extractMinute(rawDateAndTime),
												 extractSecond(rawDateAndTime));

		return LocalDateTime.of(localDate, localTime);
	}

	private int extractYear(final String dateAndTime) {
		return Integer.parseInt(YEAR_PREFIX + dateAndTime.substring(0, YEAR_END_INDEX));
	}

	private int extractMonth(final String dateAndTime) {
		return Integer.parseInt(dateAndTime.substring(YEAR_END_INDEX, MONTH_END_INDEX));
	}

	private int extractDay(final String dateAndTime) {
		return Integer.parseInt(dateAndTime.substring(MONTH_END_INDEX, DAY_END_INDEX));
	}

	private int extractHour(final String dateAndTime) {
		return Integer.parseInt(dateAndTime.substring(DAY_END_INDEX, HOUR_END_INDEX));
	}

	private int extractMinute(final String dateAndTime) {
		return Integer.parseInt(dateAndTime.substring(HOUR_END_INDEX, MINUTE_END_INDEX));
	}

	private int extractSecond(final String dateAndTime) {
		return Integer.parseInt(dateAndTime.substring(MINUTE_END_INDEX, SECOND_END_INDEX));
	}
}
