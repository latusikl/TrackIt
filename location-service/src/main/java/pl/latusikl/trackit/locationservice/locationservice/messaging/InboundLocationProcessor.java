package pl.latusikl.trackit.locationservice.locationservice.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface InboundLocationProcessor {

	String LOCATION_INPUT = "locationChannel";

	@Input(LOCATION_INPUT)
	SubscribableChannel locationChannel();

}
