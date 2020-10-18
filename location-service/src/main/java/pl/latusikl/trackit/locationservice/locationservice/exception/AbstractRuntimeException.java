package pl.latusikl.trackit.locationservice.locationservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractRuntimeException extends RuntimeException {

	private final HttpStatus httpStatus;
	private final String additionalInformation;

	public AbstractRuntimeException(final String message, final HttpStatus httpStatus, final String additionalInformation) {
		super(message);
		this.httpStatus = httpStatus;
		this.additionalInformation = additionalInformation;
	}

}
