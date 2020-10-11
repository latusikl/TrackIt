package pl.latusikl.trackit.trackerservice.server.coban.excpetions;

public class MessageParsingException extends AbstractRuntimeException {
	public MessageParsingException(final String message, final Class classType) {
		super(message, classType);
	}
}
