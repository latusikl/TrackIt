package pl.latusikl.trackit.trackerservice.server.coban.excpetions;

import pl.latusikl.trackit.trackerservice.server.common.AbstractRuntimeException;

public class InterceptorException extends AbstractRuntimeException {

	public InterceptorException(String message, Class classType, String possibleCauseOfException, String additionalInformation) {
		super(message, classType, possibleCauseOfException, additionalInformation);
	}
}
