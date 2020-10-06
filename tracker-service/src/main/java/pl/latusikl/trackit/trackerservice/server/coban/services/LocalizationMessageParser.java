package pl.latusikl.trackit.trackerservice.server.coban.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.trackerservice.server.coban.dto.LocalizationMessageDto;
import pl.latusikl.trackit.trackerservice.server.coban.excpetions.MessageParsingException;

import static pl.latusikl.trackit.trackerservice.properties.CobanConstants.IMEI_PREFIX;
import static pl.latusikl.trackit.trackerservice.properties.CobanConstants.LocationPacket;
import static pl.latusikl.trackit.trackerservice.properties.CobanConstants.PACKET_SPLIT_CHAR;

/**
 * Message validation should be done before usage
 */
@Slf4j
@Service
public class LocalizationMessageParser {

	public LocalizationMessageDto parse(final String message) {
		final String[] splitMessage = message.split(PACKET_SPLIT_CHAR);
		try {
			return LocalizationMessageDto.builder()
										 .imei(extractImei(splitMessage))
										 .dateTime(splitMessage[LocationPacket.DATE_TIME_POSITION])
										 .gpsConnectionStatus(extractGpsConnectionStatus(splitMessage))
										 .latitude(extractLatitude(splitMessage))
										 .longitude(extractLongitude(splitMessage))
										 .build();
		}
		catch (final RuntimeException e) {
			throw new MessageParsingException("Unable to process recieved message", LocalizationMessageParser.class);
		}

	}

	private String extractImei(final String[] splitMessage) {
		final String imeiPart = splitMessage[LocationPacket.IMEI_POSITION];
		return imeiPart.substring(IMEI_PREFIX.length());
	}

	private boolean extractGpsConnectionStatus(final String[] splitMessage) {
		final String gpsStatusPart = splitMessage[LocationPacket.GPS_STATUS_LOCATION];
		return gpsStatusPart.equals(LocationPacket.GPS_STATUS_OK);
	}

	private String extractLatitude(final String[] splitMessage) {
		return extractCoordinates(splitMessage, LocationPacket.LATITUDE_POSITION, LocationPacket.LATITUDE_DIRECTION);
	}

	private String extractLongitude(final String[] splitMessage) {
		final String longitude = splitMessage[LocationPacket.LONGITUDE_POSITION];
		final String direction = splitMessage[LocationPacket.LONGITUDE_DIRECTION];
		return longitude + direction;
	}

	private String extractCoordinates(final String[] splitMessage, final int valuePosition, final int directionPosition) {
		return splitMessage[valuePosition] + splitMessage[directionPosition];
	}
}
