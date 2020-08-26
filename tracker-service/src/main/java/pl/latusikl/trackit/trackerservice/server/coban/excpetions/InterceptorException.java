package pl.latusikl.trackit.trackerservice.server.coban.excpetions;

public class InterceptorException
        extends RuntimeException
{
    private InterceptorException(final String message)
    {
        super(message);
    }

    public static InterceptorException createInstance(final String message, final Class interceptorClass)
    {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Occurred in ");
        stringBuilder.append(interceptorClass.getSimpleName());
        stringBuilder.append('.');
        stringBuilder.append(" Message: '");
        stringBuilder.append(message);
        stringBuilder.append('\'');
        return new InterceptorException(stringBuilder.toString());
    }
}
