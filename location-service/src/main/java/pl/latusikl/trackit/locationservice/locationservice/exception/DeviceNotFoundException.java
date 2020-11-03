package pl.latusikl.trackit.locationservice.locationservice.exception;

import org.springframework.http.HttpStatus;

public class DeviceNotFoundException extends AbstractRuntimeException {
	private static final HttpStatus HTTP_STATUS =  HttpStatus.NOT_FOUND;

	public DeviceNotFoundException(final String message, final String additionalInformation) {
		super(message, HTTP_STATUS, additionalInformation);
	}

	public DeviceNotFoundException(final String message) {
		super(message, HTTP_STATUS, "");
	}
}
