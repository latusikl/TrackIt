package pl.latusikl.trackit.trackerservice.server.coban.services.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.trackerservice.server.coban.services.parsers.LocalizationMessageParser;

@Slf4j
@Service
@RequiredArgsConstructor
public class CobanLocationMessageHandler implements MessageHandler {

	private final MessageChannel cobanLocationChannel;
	private final LocalizationMessageParser localizationMessageParser;

	@Override
	public void handleMessage(final Message<?> message) throws MessagingException {
		log.warn(localizationMessageParser.parse(message.getPayload()
														.toString())
										  .toString());
	}
}
