package pl.latusikl.trackit.trackerservice.server.coban.services.parsers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.trackerservice.server.coban.constatns.LocationPacketConstants;
import pl.latusikl.trackit.trackerservice.server.coban.dto.LocalizationMessageDto;
import pl.latusikl.trackit.trackerservice.server.coban.excpetions.MessageParsingException;

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
	private final LocationPacketConstants locationPacketConstants;

	public LocalizationMessageDto parse(final String message) {
		final String[] splitMessage = message.split(locationPacketConstants.getPacketSplitChar());
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
		final String imeiPart = splitMessage[locationPacketConstants.getImeiPosition()];
		return imeiPart.substring(locationPacketConstants.getImeiPrefix()
														 .length());
	}

	private boolean extractGpsConnectionStatus(final String[] splitMessage) {
		final String gpsStatusPart = splitMessage[locationPacketConstants.getGpsStatusLocation()];
		return gpsStatusPart.equals(locationPacketConstants.getGpsStatusOk());
	}

}
