package pl.latusikl.trackit.trackerservice.messaging.dto.access;

import lombok.Value;

@Value
public class AccessRequestDto {

	private String imei;
	private AccessRequestType requestType;
}
