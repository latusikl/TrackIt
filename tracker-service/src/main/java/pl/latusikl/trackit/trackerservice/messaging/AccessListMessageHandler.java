package pl.latusikl.trackit.trackerservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import pl.latusikl.trackit.trackerservice.messaging.dto.access.AccessRequestCallbackDto;
import pl.latusikl.trackit.trackerservice.messaging.dto.access.AccessRequestDto;
import pl.latusikl.trackit.trackerservice.messaging.dto.access.AccessRequestStatus;

@Slf4j
@RequiredArgsConstructor
@EnableBinding({InboundProcessor.class, OutboundProcessor.class})
public class AccessListMessageHandler {

	private final OutboundProcessor outboundProcessor;

	@StreamListener(InboundProcessor.ACCESS_LIST_CHANNEL)
	public void handleDeviceAccessRequest(final AccessRequestDto accessRequestDto) {
		log.error(accessRequestDto.toString());

		final var accessRequestCallbackStatus = AccessRequestCallbackDto.builder()
																		.accessRequestStatus(AccessRequestStatus.FINISHED)
																		.requestInformation("All good ;)")
																		.build();

		outboundProcessor.accessListCallbackChannel()
						 .send(MessageBuilder.withPayload(accessRequestCallbackStatus)
											 .build());

	}
}
