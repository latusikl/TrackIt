package pl.latusikl.trackit.locationservice.locationservice.messaging.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import pl.latusikl.trackit.locationservice.locationservice.messaging.InboundProcessor;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestCallbackDto;

@Slf4j
@RequiredArgsConstructor
@EnableBinding(InboundProcessor.class)
public class AccessCallbackMessageProcessor {

	@StreamListener(InboundProcessor.ALLOW_LIST_CALLBACK)
	public void handleAccessMessageCallback(final AccessRequestCallbackDto accessRequestCallbackStatusDto) {
		log.error(accessRequestCallbackStatusDto.toString());
	}
}
