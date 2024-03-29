package pl.latusikl.trackit.trackerservice.server.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionExceptionEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;
import org.springframework.integration.ip.tcp.serializer.SoftEndOfStreamException;
import pl.latusikl.trackit.trackerservice.server.constatns.ServerConstants;
import pl.latusikl.trackit.trackerservice.server.services.ConnectionEventService;
import pl.latusikl.trackit.trackerservice.server.services.ExceptionEventService;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TcpServerEventConfiguration
{

    private final ExceptionEventService exceptionEventService;
    private final ConnectionEventService connectionEventService;
    private final ServerConstants serverConstants;

    @EventListener
    public void open(final TcpConnectionOpenEvent event)
    {
        if (event.getConnectionFactoryName().equals(serverConstants.getServerBeanName())) {
            log.debug("New connection established Connection id: {}", event.getConnectionId());
            connectionEventService.handleConnectionOpen(event.getConnectionId());
        }
    }

    @EventListener
    public void exception(final TcpConnectionExceptionEvent exceptionEvent)
    {
        if (exceptionEvent.getCause() instanceof SoftEndOfStreamException) {
            log.debug(exceptionEvent.getCause().getMessage());
        }
        if (exceptionEvent.getConnectionFactoryName().equals(serverConstants.getServerBeanName())) {
            exceptionEventService.handleExceptionEvent(exceptionEvent);
        }
    }

    @EventListener
    public void close(final TcpConnectionCloseEvent event)
    {
        if (event.getConnectionFactoryName().equals(serverConstants.getServerBeanName())) {
            connectionEventService.handleConnectionClosed(event.getConnectionId());
        }
    }

}
