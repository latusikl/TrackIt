package pl.latusikl.trackit.locationservice.locationservice.messaging.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import pl.latusikl.trackit.locationservice.locationservice.messaging.InboundProcessor;
import pl.latusikl.trackit.locationservice.locationservice.messaging.services.AllAllowedSendingService;

@Slf4j
@RequiredArgsConstructor
@EnableBinding(InboundProcessor.class)
public class RequestAllAllowedDevicesProcessor {

	private final AllAllowedSendingService allAllowedSendingService;

	@StreamListener(InboundProcessor.REQUEST_ALL_ALLOWED)
	public void handleAccessMessageCallback(final String value) {
		log.error(value);
		allAllowedSendingService.sendAllAllowedDevicesAccessRequest();
	}
}
