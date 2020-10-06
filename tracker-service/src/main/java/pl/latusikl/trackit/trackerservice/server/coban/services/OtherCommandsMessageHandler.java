package pl.latusikl.trackit.trackerservice.server.coban.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OtherCommandsMessageHandler
        implements MessageHandler
{
    @Override
    public void handleMessage(final Message<?> message) throws MessagingException
    {
        log.warn("Message channel 2: {}", message.getPayload().toString());
    }
}
