package pl.latusikl.trackit.trackerservice.server.coban.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LocalizationMessageHandler implements MessageHandler {

	private final MessageChannel cobanLocalizationChannel;
	private final LocalizationMessageParser localizationMessageParser;

	@Override
	public void handleMessage(final Message<?> message) throws MessagingException {
		log.warn(localizationMessageParser.parse(message.getPayload()
														.toString())
										  .toString());
	}
}
