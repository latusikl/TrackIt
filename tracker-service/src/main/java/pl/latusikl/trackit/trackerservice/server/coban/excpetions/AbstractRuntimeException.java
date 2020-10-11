package pl.latusikl.trackit.trackerservice.server.coban.excpetions;

import lombok.Getter;

@Getter
public abstract class AbstractRuntimeException extends RuntimeException {

	public AbstractRuntimeException(final String message, final Class classType) {
		super(prepareMessage(message, classType));
		this.classType=classType;
	}

	public AbstractRuntimeException(final String message, final Class classType, final String possibleCauseOfException, final String additionalInformation) {
		super(prepareMessage(message, classType));
		this.classType = classType;
		this.possibleCauseOfException = possibleCauseOfException;
		this.additionalInformation = additionalInformation;
	}

	private Class classType;
	private String possibleCauseOfException;
	private String additionalInformation;

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