package pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccessRequestDto {

	String imei;
	AccessRequestType requestType;
}
