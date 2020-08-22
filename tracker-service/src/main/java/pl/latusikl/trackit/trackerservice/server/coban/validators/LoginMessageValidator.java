package pl.latusikl.trackit.trackerservice.server.coban.validators;

import lombok.extern.slf4j.Slf4j;
import pl.latusikl.trackit.trackerservice.server.coban.configuration.CobanConstants;

import java.util.function.Predicate;

/**
 * //Expected format: ##,imei:123456789123456,A;
 */
@Slf4j
class LoginMessageValidator
        implements Predicate<String>
{

    private static final String LOGIN_HEADER = "##";

    private static final String LOGIN_PACKET_SUFFIX = "A;";

    private static final int LOGIN_PACKET_SIZE_AFTER_SPLIT = 3;

    @Override
    public boolean test(final String s)
    {
        final String[] splitMessage = s.trim().split(CobanConstants.PACKET_SPLIT_CHAR);
        if (splitMessage.length != LOGIN_PACKET_SIZE_AFTER_SPLIT) {
            logValidationError("split length", String.valueOf(splitMessage.length),
                               String.valueOf(LOGIN_PACKET_SIZE_AFTER_SPLIT));
            return false;
        }
        if (!splitMessage[0].equals(LOGIN_HEADER)) {
            logValidationError("message header", splitMessage[0], LOGIN_HEADER);
            return false;
        }
        if (!splitMessage[1].startsWith(CobanConstants.IMEI_PREFIX)) {
            logValidationError("imei prefix", splitMessage[1], CobanConstants.IMEI_PREFIX + "<imei>");
            return false;
        }
        if (!splitMessage[2].equals(LOGIN_PACKET_SUFFIX)) {
            logValidationError("message suffix", splitMessage[2], LOGIN_PACKET_SUFFIX);
            return false;
        }

        return true;
    }

    private void logValidationError(final String cause, final String actual, final String expected)
    {
        log.debug("Login message validation failed. Caused by: {} Actual: {} . Expected: {} .", cause, actual,
                  expected);
    }
}
