package pl.latusikl.trackit.trackerservice.server.coban.services.interceptors;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorFactory;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorSupport;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ConnectionRepository;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ImeiRepository;
import pl.latusikl.trackit.trackerservice.server.coban.constatns.LoginPacketConstants;

import java.util.function.Predicate;

@RequiredArgsConstructor
@Component
public class ConnectionLoginInterceptorFactory implements TcpConnectionInterceptorFactory, ApplicationEventPublisherAware {

	private final ImeiRepository imeiRepository;
	private final ConnectionRepository connectionRepository;
	private final Predicate<String> locationMessageValidator;
	private final Predicate<String> loginMessageValidator;
	private final LoginPacketConstants loginPacketConstants;

	private volatile ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public TcpConnectionInterceptorSupport getInterceptor() {
		return new ConnectionLoginInterceptor(applicationEventPublisher, Transformers.objectToString(), imeiRepository,
											  connectionRepository, locationMessageValidator, loginMessageValidator,
											  loginPacketConstants);
	}
}
