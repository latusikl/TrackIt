package pl.latusikl.trackit.trackerservice.server.coban;

/**
 * Constants for tcp server prepared for Coban GPS trackers.
 */
public class CobanConstants {
	public static final String IMEI_PREFIX = "imei:";

	public static final String PACKET_SPLIT_CHAR = ",";

	public static final String LOCALIZATION_CHANNEL = "cobanLocalizationChannel";

	public static final String OTHER_COMMAND_CHANNEL = "cobanOtherCommandsChannel";

	public static final String SERVER_BEAN_NAME = "cobanServer";

	public static final int MAX_LOGIN_RETRY = 2;

	//Login Packet
	public static class LoginPacket {

		public static final String HEADER = "##";

		public static final String SUFFIX = "A;";

		public static final int PREFIX_POSITION = 0;

		public static final int IMEI_POSITION = 1;

		public static final int SUFFIX_POSITION = 2;

		public static final int PACKET_SIZE = 3;

		public static final String SERVER_RESPONSE = "LOAD";

	}

	public static class LocationPacket {
		public static final String KEYWORD_VER_1 = "tracker";

		public static final String KEYWORD_VER_2 = "001";

		public static final String GPS_STATUS_OK = "F";

		public static final String GPS_STATUS_LOW = "L";

		public static final int PACKET_SIZE = 13;

		public static final int IMEI_POSITION = 0;

		public static final int DATE_TIME_POSITION = 2;

		public static final int KEYWORD_POSITION = 1;

		public static final int GPS_STATUS_LOCATION = 4;

		public static final int LATITUDE_POSITION = 7;
		public static final int LATITUDE_DIRECTION = 8;

		public static final int LONGITUDE_POSITION = 9;

		public static final int LONGITUDE_DIRECTION = 10;
	}
}