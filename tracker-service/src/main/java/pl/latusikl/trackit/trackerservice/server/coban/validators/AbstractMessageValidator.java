package pl.latusikl.trackit.trackerservice.server.coban.validators;

import lombok.extern.slf4j.Slf4j;
import pl.latusikl.trackit.trackerservice.server.coban.CobanConstants;

import java.util.function.Predicate;

@Slf4j
abstract class AbstractMessageValidator
        implements Predicate<String>
{

    boolean isSplitLengthValid(final String[] splitMessage, final int expectedLength)
    {
        return splitMessage.length == expectedLength;
    }

    boolean isImeiPrefixed(final String partOfSplit){
        return partOfSplit.startsWith(CobanConstants.IMEI_PREFIX);
    }

    @Override
    public abstract boolean test(final String s);

    protected void logValidationError(final Class clazz, final String actual)
    {
        log.debug("Message validation failed. Validator: '{}'. Message: '{}'.", clazz.getName(), actual);
    }
}
