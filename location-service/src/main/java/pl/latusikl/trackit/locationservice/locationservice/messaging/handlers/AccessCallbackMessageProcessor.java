package pl.latusikl.trackit.locationservice.locationservice.messaging.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import pl.latusikl.trackit.locationservice.locationservice.messaging.InboundProcessor;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestCallbackDto;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestStatus;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestType;
import pl.latusikl.trackit.locationservice.locationservice.messaging.services.LocationCallbackProcessingService;
import pl.latusikl.trackit.locationservice.locationservice.web.service.DeviceInfoMessageService;

@Slf4j
@RequiredArgsConstructor
@EnableBinding(InboundProcessor.class)
public class AccessCallbackMessageProcessor {

	private final LocationCallbackProcessingService locationCallbackProcessingService;

	@StreamListener(InboundProcessor.ALLOW_LIST_CALLBACK)
	public void handleAccessMessageCallback(final AccessRequestCallbackDto callbackDto) {
		locationCallbackProcessingService.handle(callbackDto);
	}

}
