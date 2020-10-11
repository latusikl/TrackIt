package pl.latusikl.trackit.trackerservice.server.coban.excpetions;

import pl.latusikl.trackit.trackerservice.server.common.AbstractRuntimeException;

public class MessageParsingException extends AbstractRuntimeException {
	public MessageParsingException(String message, Class classType, String possibleCauseOfException, String additionalInformation) {
		super(message, classType, possibleCauseOfException, additionalInformation);
	}
}
