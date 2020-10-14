package pl.latusikl.trackit.trackerservice.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface InboundProcessor {

	String ACCESS_LIST_CHANNEL = "accessList";

	@Input(ACCESS_LIST_CHANNEL)
	SubscribableChannel accessListChannel();

}
