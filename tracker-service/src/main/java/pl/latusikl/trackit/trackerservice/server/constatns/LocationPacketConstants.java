package pl.latusikl.trackit.trackerservice.server.constatns;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@NoArgsConstructor
public class LocationPacketConstants {
	private final String keywordVer1 = "tracker";

	private final String keywordVer2 = "001";

	private final String gpsStatusOk = "F";

	private final String gpsStatusOff = "L";

	private final int packetSize = 13;

	private final int dateTimePosition = 2;

	private final int keywordPosition = 1;

	private final int gpsStatusLocation = 4;

	private final int latitudePosition = 7;

	private final int latitudeDirection = 8;

	private final int longitudePosition = 9;

	private final int longitudeDirection = 10;

	private final String packetSplitChar = ",";

	private final int imeiPosition = 0;

	private final String imeiPrefix = "imei:";
}
