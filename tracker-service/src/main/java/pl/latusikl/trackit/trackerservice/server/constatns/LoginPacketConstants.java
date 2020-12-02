package pl.latusikl.trackit.trackerservice.server.constatns;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@NoArgsConstructor
public class LoginPacketConstants {

	private final String header = "##";

	private final String suffix = "A;";

	private final int prefixPosition = 0;

	private final int imeiPosition = 1;

	private final int suffixPosition = 2;

	private final int packetSize = 3;

	private final String serverResponse = "LOAD";

	private final String packetSplitChar = ",";

	private final String imeiPrefix = "imei:";
}
