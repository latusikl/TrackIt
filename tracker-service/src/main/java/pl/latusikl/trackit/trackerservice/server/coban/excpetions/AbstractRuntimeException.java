package pl.latusikl.trackit.trackerservice.server.coban.excpetions;

public abstract class AbstractRuntimeException extends RuntimeException {

	public AbstractRuntimeException(final String message, final Class classType) {
		super(prepareMessage(message, classType));
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
