package pl.latusikl.trackit.trackerservice.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.trackerservice.messaging.dto.access.AccessRequestCallbackDto;
import pl.latusikl.trackit.trackerservice.messaging.dto.location.LocationMessageDto;

@Service
@RequiredArgsConstructor
@EnableBinding(OutboundProcessor.class)
public class OutboundSender {

	private static final String REQUEST_ALL_MESSAGE = "REQUEST_ALL";
	private final OutboundProcessor outboundProcessor;

	public void sendDeviceAccessCallback(final AccessRequestCallbackDto accessRequestCallbackDto) {
		final Message<AccessRequestCallbackDto> message = MessageBuilder.withPayload(accessRequestCallbackDto)
																		.build();
		outboundProcessor.accessListCallbackChannel()
						 .send(message);

	}

	public void sendDeviceLocationToLocationService(final LocationMessageDto locationMessageDto) {
		final Message<LocationMessageDto> message = MessageBuilder.withPayload(locationMessageDto)
																  .build();
		outboundProcessor.locationChannel()
						 .send(message);

	}

	public void sendAllAllowedDevicesRequest() {
		final Message<String> message = MessageBuilder.withPayload(REQUEST_ALL_MESSAGE)
													  .build();
		outboundProcessor.requestAllAllowedChannel()
						 .send(message);
	}

}
