package pl.latusikl.trackit.locationservice.locationservice.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestDto;

@Service
@RequiredArgsConstructor
@EnableBinding(OutboundProcessor.class)
public class OutboundSender {

	private final OutboundProcessor outboundProcessor;

	public void sendDeviceAccessRequest(final AccessRequestDto accessRequestDto) {
		final Message<AccessRequestDto> message = MessageBuilder.withPayload(accessRequestDto)
																.build();
		outboundProcessor.accessListChannel()
						 .send(message);

	}
}
