package pl.latusikl.trackit.trackerservice.messaging.dto.access;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccessRequestCallbackDto {

	AccessRequestType accessRequestType;
	String requestInformation;
	AccessRequestStatus accessRequestStatus;
	String deviceId;

}
