package pl.latusikl.trackit.trackerservice.properties;

/**
 * Constants for tcp server prepared for Coban GPS trackers.
 */
public class CobanConstants
{
    public static final String IMEI_PREFIX = "imei:";

    public static final String PACKET_SPLIT_CHAR = ",";

    public static final String LOCALIZATION_CHANNEL = "cobanLocalizationChannel";

    public static final String OTHER_COMMAND_CHANNEL = "cobanOtherCommandsChannel";

    public static final String COBAN_SERVER_BEAN_NAME = "cobanServer";
}
