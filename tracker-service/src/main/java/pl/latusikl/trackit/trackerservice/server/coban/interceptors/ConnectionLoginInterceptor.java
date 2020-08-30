package pl.latusikl.trackit.trackerservice.server.coban.interceptors;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorSupport;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.messaging.Message;
import pl.latusikl.trackit.trackerservice.persistance.entities.ConnectionEntity;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ConnectionRepository;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ImeiRepository;
import pl.latusikl.trackit.trackerservice.properties.CobanConstants;
import pl.latusikl.trackit.trackerservice.server.coban.excpetions.InterceptorException;
import pl.latusikl.trackit.trackerservice.server.coban.validators.MessageValidators;

import java.util.Arrays;
import java.util.Optional;

/**
 * TcpConnectionInterceptor
 */
@NoArgsConstructor
@Slf4j
public class ConnectionLoginInterceptor
        extends TcpConnectionInterceptorSupport
{
    private HandshakeState handshakeState;

    private int loginRetryCounter;

    private AbstractPayloadTransformer<?,String> payloadTransformer;

    private ImeiRepository imeiRepository;

    private ConnectionRepository connectionRepository;

    public ConnectionLoginInterceptor(final ApplicationEventPublisher applicationEventPublisher, final AbstractPayloadTransformer<?,String> abstractPayloadTransformer, final ImeiRepository imeiRepository, final ConnectionRepository connectionRepository)
    {
        super(applicationEventPublisher);

        this.handshakeState = HandshakeState.NOT_STARTED;
        this.loginRetryCounter = 0;

        this.payloadTransformer = abstractPayloadTransformer;
        this.imeiRepository = imeiRepository;
        this.connectionRepository = connectionRepository;
    }

    @Override
    public boolean onMessage(final Message<?> message)
    {
        if (isServer() && handshakeState != HandshakeState.LOGGED) {
            //Only one thread can access this block
            log.debug(message.toString());
            log.debug("Payload type: {}",message.getPayload().getClass().toString());
            if(message.getPayload() instanceof String && ((String) message.getPayload()).isBlank()){
                return super.onMessage(message);
            }
            log.debug("Message received: '{}'",message.getPayload());
            log.debug("Message after transforming: '{}'", payloadTransformer.doTransform(message));
            synchronized (this) {
                try {
                    if (handshakeState == HandshakeState.NOT_STARTED) {
                        handleNotStartedStatus(message);
                    } else if (handshakeState == HandshakeState.LOGIN_RESPONSE_SEND) {
                        handleLoginResponseSendStatus(message);
                    } else if (handshakeState == HandshakeState.LOGIN_FAILED) {
                        handleLoginFailed(message);
                    }
                } catch (final Exception e) {
                    log.error(e.getMessage());
                    log.debug(Arrays.toString(e.getStackTrace()));
                    return true;
                }
            }
        }
        return super.onMessage(message);

    }

    private void handleNotStartedStatus(final Message<?> message)
    {
        final String messagePayload = payloadTransformer.doTransform(message);
        logReceivedMessage(messagePayload);

        if (isLoginMessageValid(messagePayload) && isImeiWhitelisted(messagePayload)) {

            final String imei = extractImei(messagePayload);
            saveImeiToConnectionEntity(imei);

            try {
                log.debug("Sending login confirmation. IMEI: {}", imei);

                sendMessage(CobanConstants.LoginPacket.SERVER_RESPONSE);
                handshakeState = HandshakeState.LOGIN_RESPONSE_SEND;

            } catch (final Exception e) {
                closeConnectionWithError(
                        "Login interceptor error. Connection closed. Error message: " + e.getMessage());
            }
        } else {
            closeConnectionWithError(
                    "Login interceptor error. Connection closed. Packet not as expected: '" + messagePayload + "'");
        }
    }

    private void logReceivedMessage(final String messagePayload)
    {
        log.debug("Handshake state: '{}'. Connection id: '{}'. Revieved message: {}", this.handshakeState,
                  this.getConnectionId(), messagePayload);
    }

    private boolean isLoginMessageValid(final String messagePayload)
    {
        return MessageValidators.loginMessageValidator().test(messagePayload);
    }

    private boolean isImeiWhitelisted(final String messagePayload)
    {
        return imeiRepository.isImeiWhitelisted(extractImei(messagePayload));
    }

    private String extractImei(final String messagePayload)
    {
        return messagePayload.trim().split(
                CobanConstants.PACKET_SPLIT_CHAR)[CobanConstants.LoginPacket.IMEI_POSITION].substring(
                CobanConstants.IMEI_PREFIX.length());
    }

    private void saveImeiToConnectionEntity(final String imei)
    {
        final Optional<ConnectionEntity> connectionEntityOptional = connectionRepository.findById(getConnectionId());
        if (connectionEntityOptional.isEmpty()) {
            closeConnectionWithError(
                    "Login message error. Unable to read proper connection Entity. Connection closed.");
        } else {
            final ConnectionEntity connectionEntity = connectionEntityOptional.get();
            connectionEntity.setDeviceImei(imei);
            connectionRepository.save(connectionEntity);
        }

    }

    private void closeConnectionWithError(final String errorMessage)
    {
        closeConnection(true);
        throw InterceptorException.createInstance(errorMessage, ConnectionLoginInterceptor.class);
    }

    private void sendMessage(final String messagePayload)
    {
        super.send(MessageBuilder.withPayload(messagePayload).build());
    }

    private void handleLoginResponseSendStatus(final Message<?> message)
    {
        final String messagePayload = payloadTransformer.doTransform(message);

        logReceivedMessage(messagePayload);

        if (isLocationMessageValid(messagePayload)) {
            handshakeState = HandshakeState.LOGGED;
        } else {
            handshakeState = HandshakeState.LOGIN_FAILED;
            handleLoginFailed(message);
        }
    }

    private boolean isLocationMessageValid(final String messagePayload)
    {
        return MessageValidators.locationMessageValidator().test(messagePayload);
    }

    private void handleLoginFailed(final Message<?> message)
    {
        final String messagePayload = payloadTransformer.doTransform(message);

        if (isLocationMessageValid(messagePayload)) {
            handshakeState = HandshakeState.LOGGED;
        } else if (isLoginMessageValid(messagePayload)) {
            if (loginRetryCounter == CobanConstants.MAX_LOGIN_RETRY) {
                closeConnectionWithError("Login interceptor error. Login failed after retry.");
            } else {
                sendMessage(CobanConstants.LoginPacket.SERVER_RESPONSE);
                loginRetryCounter++;
            }
        } else {
            closeConnectionWithError("After login received message with unknown payload. Connection closed.");
        }
    }
}
