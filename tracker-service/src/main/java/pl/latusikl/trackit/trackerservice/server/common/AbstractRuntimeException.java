package pl.latusikl.trackit.trackerservice.server.common;

import lombok.Getter;

@Getter
public abstract class AbstractRuntimeException extends RuntimeException {

	private Class classType;
	private String possibleCauseOfException;
	private String additionalInformation;

	public AbstractRuntimeException(final String message, final Class classType, final String possibleCauseOfException,
									final String additionalInformation) {
		super(prepareMessage(message, classType));
		this.classType = classType;
		this.possibleCauseOfException = possibleCauseOfException;
		this.additionalInformation = additionalInformation;
	}

	private static String prepareMessage(final String message, final Class classType) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Occurred in ");
		stringBuilder.append(classType.getSimpleName());
		stringBuilder.append('.');
		stringBuilder.append(" Message: '");
		stringBuilder.append(message);
		stringBuilder.append('\'');
		return stringBuilder.toString();
	}
}
