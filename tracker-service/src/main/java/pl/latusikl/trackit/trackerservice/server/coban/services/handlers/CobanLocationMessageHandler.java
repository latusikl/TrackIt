package pl.latusikl.trackit.trackerservice.server.coban.services.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.trackerservice.messaging.OutboundSender;
import pl.latusikl.trackit.trackerservice.server.coban.services.parsers.LocationMessageParser;

@Slf4j
@Service
@RequiredArgsConstructor
public class CobanLocationMessageHandler implements MessageHandler {

	private final LocationMessageParser locationMessageParser;
	private final OutboundSender outboundSender;

	@Override
	public void handleMessage(final Message<?> message) throws MessagingException {
		final var locationMessageDto = locationMessageParser.parse(message.getPayload()
																		  .toString());

		outboundSender.sendDeviceLocationToLocationService(locationMessageDto);
		log.debug(locationMessageDto.toString());
	}
}
