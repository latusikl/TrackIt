package pl.latusikl.trackit.locationservice.locationservice.exception;

import org.springframework.http.HttpStatus;

public class LocationNotFoundException extends AbstractRuntimeException {
	public LocationNotFoundException(final String message, final String additionalInformation) {
		super(message, HttpStatus.NOT_FOUND, additionalInformation);
	}
}
