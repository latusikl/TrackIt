package pl.latusikl.trackit.trackerservice.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundProcessor {

	String LOCATION_INPUT = "location";
	String ALLOW_LIST_CALLBACK = "accessListCallback";

	@Output(LOCATION_INPUT)
	MessageChannel locationChannel();

	@Output(ALLOW_LIST_CALLBACK)
	MessageChannel accessListCallbackChannel();
}
