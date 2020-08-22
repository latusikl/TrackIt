package pl.latusikl.trackit.trackerservice.server.coban.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Slf4j
public class LocalizationMessageHandler
        implements MessageHandler
{

    final MessageChannel serverOut;

    public LocalizationMessageHandler(final MessageChannel serverOut)
    {
        this.serverOut = serverOut;
    }

    @Override
    public void handleMessage(final Message<?> message) throws MessagingException
    {
        log.warn("Message channel 1: {}", message.getPayload().toString());
    }
}
