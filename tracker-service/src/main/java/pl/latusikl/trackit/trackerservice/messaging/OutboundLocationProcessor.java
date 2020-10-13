package pl.latusikl.trackit.trackerservice.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundLocationProcessor {

	String LOCATION_INPUT = "locationChannel";

	@Output(LOCATION_INPUT)
	MessageChannel locationChannel();
}
