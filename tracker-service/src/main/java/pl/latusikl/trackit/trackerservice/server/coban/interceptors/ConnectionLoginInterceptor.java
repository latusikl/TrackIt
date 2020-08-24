package pl.latusikl.trackit.trackerservice.server.coban.interceptors;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorSupport;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.messaging.Message;
import pl.latusikl.trackit.trackerservice.server.coban.configuration.CobanConstants;
import pl.latusikl.trackit.trackerservice.server.coban.validators.MessageValidators;

/**
 * TcpConnectionInterceptor
 */
@NoArgsConstructor
@Slf4j
public class ConnectionLoginInterceptor
        extends TcpConnectionInterceptorSupport
{

    private static final String LOGIN_HANDSHAKE_RESPONSE = "LOAD";

    private volatile boolean startHandshakeDone;

    private AbstractPayloadTransformer<?,String> payloadTransformer;

    public ConnectionLoginInterceptor(final ApplicationEventPublisher applicationEventPublisher, final AbstractPayloadTransformer<?,String> abstractPayloadTransformer)
    {
        super(applicationEventPublisher);
        this.startHandshakeDone = false;
        this.payloadTransformer = abstractPayloadTransformer;
    }

    private boolean isMessageValid(final String messagePayload)
    {
        return MessageValidators.loginMessageValidator().test(messagePayload);
    }

    private String extractImei(final String messagePayload)
    {
        return messagePayload.trim().split(CobanConstants.PACKET_SPLIT_CHAR)[1].substring(
                CobanConstants.IMEI_PREFIX.length());
    }

    @Override
    public void close()
    {
        super.close();
    }

    @Override
    public boolean onMessage(final Message<?> message)
    {
        //		if (!this.startHandshakeDone) {
        //			//Only one thread can access this block
        //			synchronized (this) {
        //				if (!this.startHandshakeDone) {
        //					final String messagePayload = payloadTransformer.doTransform(message);
        //					log.debug(this.toString() + "received " + messagePayload);
        //					if (isMessageValid(messagePayload)){
        //						log.error("Here imei should be saved or connection closed.");
        //						try {
        //							log.debug("Sending login confirmation. IMEI: {}", extractImei(messagePayload));
        //							super.send(MessageBuilder.withPayload(LOGIN_HANDSHAKE_RESPONSE).build());
        //							this.startHandshakeDone = true;
        //							return true;
        //						} catch (final Exception e) {
        //							throw new MessagingException("Login error", e);
        //						}
        //					} else{
        //						this.close();
        //						throw new MessagingException("Login error, packet not as expected: '" + messagePayload + "'");
        //					}
        //				}
        //			}
        //		}
        return super.onMessage(message);
    }

    @Override
    public void send(final Message<?> message)
    {
        super.send(message);
    }
}