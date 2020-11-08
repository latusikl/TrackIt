package pl.latusikl.trackit.locationservice.locationservice.exception;

import org.springframework.http.HttpStatus;

public class UserException extends AbstractRuntimeException {
	private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

	public UserException(final String message) {
		super(message, HTTP_STATUS, "");
	}
}
