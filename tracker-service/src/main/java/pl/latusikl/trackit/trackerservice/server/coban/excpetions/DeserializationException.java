package pl.latusikl.trackit.trackerservice.server.coban.excpetions;

import pl.latusikl.trackit.trackerservice.server.common.AbstractRuntimeException;

public class DeserializationException extends AbstractRuntimeException {

	public DeserializationException(final String message, final Class classType, final String possibleCauseOfException,
									final String additionalInformation) {
		super(message, classType, possibleCauseOfException, additionalInformation);
	}
}
