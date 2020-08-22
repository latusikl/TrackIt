package pl.latusikl.trackit.trackerservice.server.coban.interceptors;

import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorFactory;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorSupport;
import org.springframework.integration.transformer.ObjectToStringTransformer;

@NoArgsConstructor
public class ConnectionLoginInterceptorFactory
        implements TcpConnectionInterceptorFactory, ApplicationEventPublisherAware
{

    private volatile ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher)
    {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public TcpConnectionInterceptorSupport getInterceptor()
    {
        return new ConnectionLoginInterceptor(applicationEventPublisher, new ObjectToStringTransformer());
    }
}
