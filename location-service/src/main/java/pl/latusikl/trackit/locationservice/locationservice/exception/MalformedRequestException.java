package pl.latusikl.trackit.locationservice.locationservice.exception;

import org.springframework.http.HttpStatus;

public class MalformedRequestException extends AbstractRuntimeException {
	private static final HttpStatus status = HttpStatus.BAD_REQUEST;

	public MalformedRequestException(final String message, final String additionalInformation) {
		super(message, status, additionalInformation);
	}

	public MalformedRequestException(final String message) {
		super(message, status, "");
	}
}
