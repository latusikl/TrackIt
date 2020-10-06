package pl.latusikl.trackit.trackerservice.server.coban.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocalizationMessageDto {

	private String imei;
	private String dateTime;
	private boolean gpsConnectionStatus;
	private String latitude;
	private String longitude;

}
