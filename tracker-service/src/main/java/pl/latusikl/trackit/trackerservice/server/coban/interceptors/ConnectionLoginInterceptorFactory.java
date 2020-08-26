package pl.latusikl.trackit.trackerservice.server.coban.interceptors;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorFactory;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorSupport;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ConnectionRepository;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ImeiRepository;

@RequiredArgsConstructor
public class ConnectionLoginInterceptorFactory
        implements TcpConnectionInterceptorFactory, ApplicationEventPublisherAware
{

    private final ImeiRepository imeiRepository;
    private final ConnectionRepository connectionRepository;

    private volatile ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher)
    {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public TcpConnectionInterceptorSupport getInterceptor()
    {
        return new ConnectionLoginInterceptor(applicationEventPublisher, Transformers.objectToString(), imeiRepository,connectionRepository);
    }
}
