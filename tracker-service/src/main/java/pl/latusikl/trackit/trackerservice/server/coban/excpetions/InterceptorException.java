package pl.latusikl.trackit.trackerservice.server.coban.excpetions;

public class InterceptorException
		extends AbstractRuntimeException {

	public InterceptorException(final String message, final Class interceptorClass) {
		super(message, interceptorClass);
	}
}
