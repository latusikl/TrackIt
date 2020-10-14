package pl.latusikl.trackit.locationservice.locationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.locationservice.locationservice.messaging.OutboundProcessor;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestDto;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestType;

@Service
@RequiredArgsConstructor
@EnableBinding(OutboundProcessor.class)
public class DeviceAccessService {

	private final OutboundProcessor outboundProcessor;

	public void sendTrackingDeviceAccessRequest(final String id) {
		final var accessRequest = AccessRequestDto.builder()
												  .requestType(AccessRequestType.ADD)
												  .imei(id)
												  .build();
		outboundProcessor.accessListChannel()
						 .send(MessageBuilder.withPayload(accessRequest)
											 .build());
	}
}
