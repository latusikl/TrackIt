package pl.latusikl.trackit.trackerservice.server.coban.services.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.trackerservice.messaging.OutboundLocationProcessor;
import pl.latusikl.trackit.trackerservice.server.coban.services.parsers.LocalizationMessageParser;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableBinding(OutboundLocationProcessor.class)
public class CobanLocationMessageHandler implements MessageHandler {

	private final LocalizationMessageParser localizationMessageParser;
	private final OutboundLocationProcessor locationSource;

	@Override
	public void handleMessage(final Message<?> message) throws MessagingException {
		final String stringMessage = localizationMessageParser.parse(message.getPayload()
																			.toString())
															  .toString();

		locationSource.locationChannel()
					  .send(MessageBuilder.withPayload(stringMessage)
										  .build());
		log.warn(stringMessage);
	}
}
