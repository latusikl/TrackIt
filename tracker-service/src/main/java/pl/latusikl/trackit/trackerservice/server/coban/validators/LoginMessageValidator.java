package pl.latusikl.trackit.trackerservice.server.coban.validators;

import lombok.extern.slf4j.Slf4j;
import pl.latusikl.trackit.trackerservice.properties.CobanConstants;

/**
 * Expected format: ##,imei:123456789123456,A;
 */
@Slf4j
class LoginMessageValidator
        extends AbstractMessageValidator
{

    @Override
    public boolean test(final String s)
    {
        final String[] splitMessage = s.trim().split(CobanConstants.PACKET_SPLIT_CHAR);

        if (isSplitLengthValid(splitMessage, CobanConstants.LoginPacket.PACKET_SIZE) &&
                isPacketPrefixValid(splitMessage[CobanConstants.LoginPacket.PREFIX_POSITION]) &&
                isImeiPrefixed(splitMessage[CobanConstants.LoginPacket.IMEI_POSITION]) &&
                isPacketSuffixValid(splitMessage[CobanConstants.LoginPacket.SUFFIX_POSITION]))
        {
            return true;
        }

        logValidationError(LoginMessageValidator.class, s);
        return false;
    }

    private boolean isPacketPrefixValid(final String loginHeader)
    {
        return loginHeader.equals(CobanConstants.LoginPacket.HEADER);
    }

    private boolean isPacketSuffixValid(final String packetSuffix)
    {
        return packetSuffix.equals(CobanConstants.LoginPacket.SUFFIX);
    }

}
