package pl.latusikl.trackit.trackerservice.server.services.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorSupport;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.messaging.Message;
import pl.latusikl.trackit.trackerservice.persistance.entities.ConnectionEntity;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ConnectionRepository;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ImeiRepository;
import pl.latusikl.trackit.trackerservice.server.constatns.LoginPacketConstants;
import pl.latusikl.trackit.trackerservice.server.excpetions.InterceptorException;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Slf4j
public class ConnectionLoginInterceptor extends TcpConnectionInterceptorSupport {

	private static final int MAX_LOGIN_RETRY = 2;
	private final AbstractPayloadTransformer<?, String> payloadTransformer;
	private final ImeiRepository imeiRepository;
	private final ConnectionRepository connectionRepository;
	private final Predicate<String> locationMessageValidator;
	private final Predicate<String> loginMessageValidator;
	private final LoginPacketConstants loginPacketConstants;
	private final static String HEARTBEAT_PATTERN = "[0-9]+;";
	private static final String HEARTBEAT_RESPONSE = "ON";

	private HandshakeState handshakeState = HandshakeState.NOT_STARTED;
	private int loginRetryCounter = 0;

	public ConnectionLoginInterceptor(final ApplicationEventPublisher applicationEventPublisher,
									  final AbstractPayloadTransformer<?, String> abstractPayloadTransformer,
									  final ImeiRepository imeiRepository, final ConnectionRepository connectionRepository,
									  final Predicate<String> locationMessageValidator, final Predicate<String> loginMessageValidator,
									  final LoginPacketConstants loginPacketConstants) {
		super(applicationEventPublisher);
		this.payloadTransformer = abstractPayloadTransformer;
		this.imeiRepository = imeiRepository;
		this.connectionRepository = connectionRepository;
		this.locationMessageValidator = locationMessageValidator;
		this.loginMessageValidator = loginMessageValidator;
		this.loginPacketConstants = loginPacketConstants;
	}

	@Override
	public boolean onMessage(final Message<?> message) {
		if (isServer() && handshakeState != HandshakeState.LOGGED) {
			if (message.getPayload() instanceof String && ((String) message.getPayload()).isBlank()) {
				log.debug("Message was empty or payload was not String.");
				return super.onMessage(message);
			}
			synchronized (this) {
				try {
					log.info("Message: {}",payloadTransformer.doTransform(message));
					if(isHeartBeatMessage(message)){
						handleHeartbeatPackage(message);
					}
					else if (handshakeState == HandshakeState.NOT_STARTED) {
						handleNotStartedStatus(message);
					}
					else if (handshakeState == HandshakeState.LOGIN_RESPONSE_SEND) {
						handleLoginResponseSendStatus(message);
					}
					else if (handshakeState == HandshakeState.LOGIN_FAILED) {
						handleLoginFailed(message);
					}
				}
				catch (final Exception e) {
					log.error(e.getMessage());
					log.error(Arrays.toString(e.getStackTrace()));
					return true;
				}
			}
		}
		return super.onMessage(message);
	}

	private void handleHeartbeatPackage(final Message<?> message) {
		final String messagePayload = payloadTransformer.doTransform(message);
		log.debug("Handling heartbeat package");
		logDebugReceivedMessage(messagePayload);
		sendMessage(HEARTBEAT_RESPONSE);
	}

	private boolean isHeartBeatMessage(final Message<?> message) {
		final String messagePayload = payloadTransformer.doTransform(message);
		return Pattern.matches(HEARTBEAT_PATTERN,messagePayload);
	}

	private void handleNotStartedStatus(final Message<?> message) {
		final String messagePayload = payloadTransformer.doTransform(message);
		logDebugReceivedMessage(messagePayload);

		if (loginMessageValidator.test(messagePayload) && isImeiWhitelisted(messagePayload)) {

			final String imei = extractImei(messagePayload);
			saveImeiToConnectionEntity(imei);

			try {
				log.debug("Sending login confirmation. IMEI: {}", imei);
				sendMessage(loginPacketConstants.getServerResponse());
				handshakeState = HandshakeState.LOGIN_RESPONSE_SEND;
			}
			catch (final Exception e) {
				closeConnectionWithError("Login interceptor error. Connection closed. Error message: " + e.getMessage());
			}
		}
		else {
			closeConnectionWithError("Login interceptor error. Connection closed. Packet not as expected: '" + messagePayload + "'");
		}
	}

	private void logDebugReceivedMessage(final String messagePayload) {
		log.debug("Handshake state: '{}'. Connection id: '{}'. Revieved message: {}", this.handshakeState, this.getConnectionId(),
				  messagePayload);
	}

	private boolean isImeiWhitelisted(final String messagePayload) {
		return imeiRepository.isImeiWhitelisted(extractImei(messagePayload));
	}

	private String extractImei(final String messagePayload) {
		return messagePayload.trim()
							 .split(loginPacketConstants.getPacketSplitChar())[loginPacketConstants.getImeiPosition()].substring(
				loginPacketConstants.getImeiPrefix()
									.length());
	}

	private void saveImeiToConnectionEntity(final String imei) {
		final Optional<ConnectionEntity> connectionEntityOptional = connectionRepository.findById(getConnectionId());
		if (connectionEntityOptional.isEmpty()) {
			closeConnectionWithError("Login message error. Unable to read proper connection Entity. Connection closed.");
		}
		else {
			final ConnectionEntity connectionEntity = connectionEntityOptional.get();
			connectionEntity.setDeviceImei(imei);
			connectionRepository.save(connectionEntity);
		}
	}

	private void closeConnectionWithError(final String errorMessage) {
		closeConnection(true);
		throw new InterceptorException(errorMessage, ConnectionLoginInterceptor.class, "", "");
	}

	private void sendMessage(final String messagePayload) {
		super.send(MessageBuilder.withPayload(messagePayload)
								 .build());
	}

	private void handleLoginResponseSendStatus(final Message<?> message) {
		final String messagePayload = payloadTransformer.doTransform(message);

		logDebugReceivedMessage(messagePayload);

		if (locationMessageValidator.test(messagePayload)) {
			handshakeState = HandshakeState.LOGGED;
		}
		else {
			handshakeState = HandshakeState.LOGIN_FAILED;
			handleLoginFailed(message);
		}
	}

	private void handleLoginFailed(final Message<?> message) {
		final String messagePayload = payloadTransformer.doTransform(message);

		if (locationMessageValidator.test(messagePayload)) {
			handshakeState = HandshakeState.LOGGED;
		}
		else if (loginMessageValidator.test(messagePayload)) {
			if (loginRetryCounter == MAX_LOGIN_RETRY) {
				closeConnectionWithError("Login interceptor error. Login failed after retry.");
			}
			else {
				sendMessage(loginPacketConstants.getServerResponse());
				loginRetryCounter++;
			}
		}
		else {
			closeConnectionWithError("After login received message with unknown payload. Connection closed.");
		}
	}
}
