package pl.latusikl.trackit.locationservice.locationservice.exception;

import org.springframework.http.HttpStatus;

public class DeviceNotFoundException extends AbstractRuntimeException {
	public DeviceNotFoundException(final String message, final String additionalInformation) {
		super(message, HttpStatus.BAD_REQUEST, additionalInformation);
	}

	public DeviceNotFoundException(final String message) {
		super(message, HttpStatus.BAD_REQUEST, "");
	}
}
