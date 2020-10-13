package pl.latusikl.trackit.locationservice.locationservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@Slf4j
@RequiredArgsConstructor
@EnableBinding(InboundLocationProcessor.class)
public class LocationMessageProcessor {

	@StreamListener(InboundLocationProcessor.LOCATION_INPUT)
	public void processLocationMessage(final String message) {
		log.error(message);
	}

}
