package pl.latusikl.trackit.locationservice.locationservice.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface InboundProcessor {

	String LOCATION_INPUT = "location";
	String ALLOW_LIST_CALLBACK = "accessListCallback";
	String REQUEST_ALL_ALLOWED = "requestAllAllowed";

	@Input(ALLOW_LIST_CALLBACK)
	SubscribableChannel accessListCallbackChannel();

	@Input(LOCATION_INPUT)
	SubscribableChannel locationChannel();

	@Input(REQUEST_ALL_ALLOWED)
	SubscribableChannel requestAllAllowedChannel();
}
