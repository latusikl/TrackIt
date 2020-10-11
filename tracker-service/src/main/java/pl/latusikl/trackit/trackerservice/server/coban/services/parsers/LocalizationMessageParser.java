package pl.latusikl.trackit.trackerservice.server.coban.services.parsers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.trackerservice.server.coban.dto.LocalizationMessageDto;
import pl.latusikl.trackit.trackerservice.server.coban.excpetions.MessageParsingException;

import static pl.latusikl.trackit.trackerservice.server.coban.CobanConstants.IMEI_PREFIX;
import static pl.latusikl.trackit.trackerservice.server.coban.CobanConstants.LocationPacket;
import static pl.latusikl.trackit.trackerservice.server.coban.CobanConstants.PACKET_SPLIT_CHAR;

/**
 Message validation should be done before usage
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocalizationMessageParser {

	private final LocalizationDateTimeParser localizationDateTimeParser;
	private final LocationParser longitudeParser;
	private final LocationParser latitudeParser;

	public LocalizationMessageDto parse(final String message) {
		final String[] splitMessage = message.split(PACKET_SPLIT_CHAR);
		try {
			return LocalizationMessageDto.builder()
										 .imei(extractImei(splitMessage))
										 .dateTime(localizationDateTimeParser.extractDateAndTime(splitMessage))
										 .gpsConnectionStatus(extractGpsConnectionStatus(splitMessage))
										 .latitude(latitudeParser.parse(splitMessage))
										 .longitude(longitudeParser.parse(splitMessage))
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

}
