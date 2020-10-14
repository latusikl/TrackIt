package pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access;

import lombok.Value;

@Value
public class AccessRequestCallbackDto {

	String requestInformation;
	AccessRequestStatus accessRequestStatus;

}
