package pl.latusikl.trackit.locationservice.locationservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestCallbackStatusDto;

@Slf4j
@RequiredArgsConstructor
@EnableBinding(InboundProcessor.class)
public class AccessCallbackMessageHandler {

	@StreamListener(InboundProcessor.ALLOW_LIST_CALLBACK)
	public void handleAccessMessageCallback(final AccessRequestCallbackStatusDto accessRequestCallbackStatusDto) {
		log.error(accessRequestCallbackStatusDto.toString());
	}
}
