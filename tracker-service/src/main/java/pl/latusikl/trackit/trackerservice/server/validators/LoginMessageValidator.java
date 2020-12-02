package pl.latusikl.trackit.trackerservice.server.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.server.constatns.LoginPacketConstants;

/**
 Validates if message is login message by checking parts of its format. Expected format: ##,imei:123456789123456,A;
 */
@Component
@RequiredArgsConstructor
class LoginMessageValidator extends AbstractMessageValidator {

	private final LoginPacketConstants loginPacketConstants;

	@Override
	public boolean test(final String s) {
		final String[] splitMessage = s.trim()
									   .split(loginPacketConstants.getPacketSplitChar());

		if (isSplitLengthValid(splitMessage, loginPacketConstants.getPacketSize()) && isPacketPrefixValid(
				splitMessage[loginPacketConstants.getPrefixPosition()]) && isImeiPrefixed(
				splitMessage[loginPacketConstants.getImeiPosition()],loginPacketConstants.getImeiPrefix()) && isPacketSuffixValid(
				splitMessage[loginPacketConstants.getSuffixPosition()])) {
			return true;
		}

		logValidationError(LoginMessageValidator.class, s);
		return false;
	}

	private boolean isPacketPrefixValid(final String loginHeader) {
		return loginHeader.equals(loginPacketConstants.getHeader());
	}

	private boolean isPacketSuffixValid(final String packetSuffix) {
		return packetSuffix.equals(loginPacketConstants.getSuffix());
	}

}
