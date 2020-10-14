package pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access;

import lombok.Value;

@Value
public class AccessRequestCallbackStatusDto {

	String requestInformation;
	AccessRequestStatus accessRequestStatus;

}
