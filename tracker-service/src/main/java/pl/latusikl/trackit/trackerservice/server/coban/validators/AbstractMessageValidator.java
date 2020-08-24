package pl.latusikl.trackit.trackerservice.server.coban.validators;

import lombok.extern.slf4j.Slf4j;
import pl.latusikl.trackit.trackerservice.properties.CobanConstants;

import java.util.function.Predicate;

@Slf4j
abstract class AbstractMessageValidator
        implements Predicate<String>
{

    boolean isSplitLengthValid(final String[] splitMessage, final int expectedLength)
    {
        if (splitMessage.length != expectedLength) {
            logValidationError("split length", String.valueOf(splitMessage.length),
                               String.valueOf(CobanConstants.LoginPacket.PACKET_SIZE));
            return true;
        }
        return false;
    }

    boolean isImeiPrefixed(final String partOfSplit){
        if(partOfSplit.startsWith(CobanConstants.IMEI_PREFIX)){
                logValidationError("imei prefix", partOfSplit, CobanConstants.IMEI_PREFIX + "<imei>");
                return true;
        }
        return false;
    }


    @Override
    public abstract boolean test(final String s);

    protected void logValidationError(final String cause, final String actual, final String expected)
    {
        log.warn("Message validation failed. Caused by: {} Actual: {} . Expected: {} .", cause, actual, expected);
    }
}
