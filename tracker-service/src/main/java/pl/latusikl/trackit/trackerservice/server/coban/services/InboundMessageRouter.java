package pl.latusikl.trackit.trackerservice.server.coban.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.router.AbstractMappingMessageRouter;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.messaging.Message;
import pl.latusikl.trackit.trackerservice.server.coban.configuration.CobanConstants;

import java.util.Collections;
import java.util.List;

@Slf4j
public class InboundMessageRouter
        extends AbstractMappingMessageRouter
{

    private static final long CHANNEL_SEND_TIMEOUT = 600000; //10 minutes

    private AbstractPayloadTransformer<?,String> payloadTransformer;

    public InboundMessageRouter(final AbstractPayloadTransformer<?,String> payloadTransformerr)
    {
        super();
        setDefaultOutputChannel(new NullChannel());
        setSendTimeout(CHANNEL_SEND_TIMEOUT);
        this.payloadTransformer = payloadTransformerr;
    }

    @Override
    protected List<Object> getChannelKeys(final Message<?> message)
    {
        final String messagePayload = payloadTransformer.doTransform(message);
        log.debug(this.toString() + "received " + messagePayload);

        String channelName = null;

        if (messagePayload.contains("test1")) {
            channelName = CobanConstants.LOCALIZATION_CHANNEL;
        }

        if (messagePayload.contains("test2")) {
            channelName = CobanConstants.OTHER_COMMAND_CHANNEL;
        }
        return Collections.singletonList(channelName);
    }
}
