package pl.latusikl.trackit.trackerservice.server.coban.excpetions;

public class DeserializationException extends AbstractRuntimeException{
	public DeserializationException(String message, Class classType) {
		super(message, classType);
	}

	public DeserializationException(final String message, final Class classType, final String possibleCauseOfException, final String additionalInformation) {
		super(message, classType, possibleCauseOfException, additionalInformation);
	}
}
