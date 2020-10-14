package pl.latusikl.trackit.trackerservice.messaging.dto.access;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccessRequestCallbackStatusDto {

	String requestInformation;
	AccessRequestStatus accessRequestStatus;

}
