package pl.latusikl.trackit.trackerservice.server.coban.validators;

import lombok.extern.slf4j.Slf4j;
import pl.latusikl.trackit.trackerservice.properties.CobanConstants;

/**
 * Expected format: imei:864926030089768,tracker,200815104703,,F,104703.00,A,5126.54672,N,01258.35332,E,0.069,0;
 * To make validation faster not all is validated. Only 4 conditions are taken into account.
 */
@Slf4j
class LocationMessageValidator
        extends AbstractMessageValidator
{
    @Override
    public boolean test(final String s)
    {
        final String[] splitMessage = s.split(CobanConstants.PACKET_SPLIT_CHAR);

        if( isSplitLengthValid(splitMessage, CobanConstants.LocationPacket.PACKET_SIZE) &&
                isImeiPrefixed(splitMessage[CobanConstants.LocationPacket.IMEI_POSITION]) &&
                containsPacketKeyWord(splitMessage[CobanConstants.LocationPacket.KEYWORD_POSITION]) &&
                containsGpsStatus(splitMessage[CobanConstants.LocationPacket.GPS_STATUS_LOCATION]))
        {
            return true;
        }
        logValidationError(LoginMessageValidator.class,s);
        return false;
    }

    private boolean containsPacketKeyWord(final String possibleKeyword)
    {
        return possibleKeyword.equals(CobanConstants.LocationPacket.KEYWORD_VER_1) ||
                possibleKeyword.equals(CobanConstants.LocationPacket.KEYWORD_VER_2);
    }

    private boolean containsGpsStatus(final String possibleStatus){
        return possibleStatus.equals(CobanConstants.LocationPacket.GPS_STATUS_1) ||
                possibleStatus.equals(CobanConstants.LocationPacket.GPS_STATUS_2);
    }
}
