package pl.latusikl.trackit.locationservice.locationservice.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface InboundProcessor {

	String LOCATION_INPUT = "location";
	String ALLOW_LIST_CALLBACK = "accessListCallback";

	@Input(ALLOW_LIST_CALLBACK)
	SubscribableChannel accessListCallbackChannel();

	@Input(LOCATION_INPUT)
	SubscribableChannel locationChannel();

}
