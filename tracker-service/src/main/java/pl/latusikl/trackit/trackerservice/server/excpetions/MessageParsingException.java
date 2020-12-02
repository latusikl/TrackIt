package pl.latusikl.trackit.trackerservice.server.excpetions;

import pl.latusikl.trackit.trackerservice.server.common.AbstractRuntimeException;

public class MessageParsingException extends AbstractRuntimeException {
	public MessageParsingException(final String message, final Class<?> classType, final String possibleCauseOfException, final String additionalInformation) {
		super(message, classType, possibleCauseOfException, additionalInformation);
	}
}
