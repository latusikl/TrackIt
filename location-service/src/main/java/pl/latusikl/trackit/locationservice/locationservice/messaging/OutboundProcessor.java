package pl.latusikl.trackit.locationservice.locationservice.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundProcessor {

	String ACCESS_LIST_CHANNEL = "accessList";

	@Output(ACCESS_LIST_CHANNEL)
	MessageChannel accessListChannel();

}
