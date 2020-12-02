package pl.latusikl.trackit.trackerservice.server.validators;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.server.constatns.LocationPacketConstants;

/**
 Expected format: imei:864926030089768,tracker,200815104703,,F,104703.00,A,5126.54672,N,01258.35332,E,0.069,0; To make validation faster
 not all is validated. Only 4 conditions are taken into account.
 */
@Slf4j
@Component
@RequiredArgsConstructor
class LocationMessageValidator extends AbstractMessageValidator {

	private final LocationPacketConstants locationPacketConstants;

	@Override
	public boolean test(final String s) {
		final String[] splitMessage = s.split(locationPacketConstants.getPacketSplitChar());

		if (isSplitLengthValid(splitMessage, locationPacketConstants.getPacketSize()) && isImeiPrefixed(
				splitMessage[locationPacketConstants.getImeiPosition()],locationPacketConstants.getImeiPrefix()) && containsPacketKeyWord(
				splitMessage[locationPacketConstants.getKeywordPosition()]) && containsGpsStatus(
				splitMessage[locationPacketConstants.getGpsStatusLocation()])) {
			return true;
		}
		logValidationError(LoginMessageValidator.class, s);
		return false;
	}

	private boolean containsPacketKeyWord(final String possibleKeyword) {
		return possibleKeyword.equals(locationPacketConstants.getKeywordVer1()) || possibleKeyword.equals(
				locationPacketConstants.getKeywordVer2());
	}

	private boolean containsGpsStatus(final String possibleStatus) {
		return possibleStatus.equals(locationPacketConstants.getGpsStatusOk()) || possibleStatus.equals(
				locationPacketConstants.getGpsStatusOff());
	}
}
