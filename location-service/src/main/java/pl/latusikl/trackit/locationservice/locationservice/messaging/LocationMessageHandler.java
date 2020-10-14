package pl.latusikl.trackit.locationservice.locationservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.location.LocationMessageDto;

@Slf4j
@RequiredArgsConstructor
@EnableBinding(InboundProcessor.class)
public class LocationMessageHandler {

	@StreamListener(InboundProcessor.LOCATION_INPUT)
	public void handleLocationMessage(final LocationMessageDto locationMessageDto) {
		log.error(locationMessageDto.toString());
	}
}
