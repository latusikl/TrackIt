package pl.latusikl.trackit.trackerservice.server.coban.validators;

import lombok.extern.slf4j.Slf4j;
import pl.latusikl.trackit.trackerservice.server.coban.constatns.CobanConstants;

import java.util.function.Predicate;

@Slf4j
abstract class AbstractMessageValidator implements Predicate<String> {

	protected boolean isSplitLengthValid(final String[] splitMessage, final int expectedLength) {
		return splitMessage.length == expectedLength;
	}

	protected boolean isImeiPrefixed(final String partOfSplit, final String imeiPrefix) {
		return partOfSplit.startsWith(imeiPrefix);
	}

	protected void logValidationError(final Class clazz, final String actual) {
		log.debug("Message validation failed. Validator: '{}'. Message: '{}'.", clazz.getName(), actual);
	}
}
