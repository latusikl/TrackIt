package pl.latusikl.trackit.trackerservice.server.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.router.AbstractMappingMessageRouter;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.server.constatns.ServerConstants;
import pl.latusikl.trackit.trackerservice.server.services.handlers.LocationMessageHandler;
import pl.latusikl.trackit.trackerservice.server.services.handlers.OtherCommandsMessageHandler;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 Custom message router. Location message are passed to different channel and different handler then other received messages.

 @see LocationMessageHandler
 @see OtherCommandsMessageHandler */
@Slf4j
@Component
public class InboundMessageRouter extends AbstractMappingMessageRouter {

	private static final long CHANNEL_SEND_TIMEOUT = 600000; //10 minutes

	private final AbstractPayloadTransformer<?, String> objectToString;
	private final Predicate<String> locationMessageValidator;
	private final ServerConstants serverConstants;

	public InboundMessageRouter(final AbstractPayloadTransformer<?, String> objectToString,
								final Predicate<String> locationMessageValidator, final ServerConstants serverConstants) {
		this.objectToString = objectToString;
		this.locationMessageValidator = locationMessageValidator;
		this.serverConstants = serverConstants;
		setDefaultOutputChannel(new NullChannel());
		setSendTimeout(CHANNEL_SEND_TIMEOUT);
	}

	@Override
	protected List<Object> getChannelKeys(final Message<?> message) {
		final String messagePayload = objectToString.doTransform(message);
		log.debug(this.toString() + "received " + messagePayload);

		final String channelName;

		if (locationMessageValidator.test(messagePayload)) {
			channelName = serverConstants.getLocationChannel();
		}
		else {
			channelName = serverConstants.getOtherCommandsChannel();
		}
		return Collections.singletonList(channelName);
	}
}
