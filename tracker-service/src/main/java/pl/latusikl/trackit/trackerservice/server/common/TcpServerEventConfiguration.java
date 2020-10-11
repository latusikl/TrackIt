package pl.latusikl.trackit.trackerservice.server.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionExceptionEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;
import org.springframework.integration.ip.tcp.serializer.SoftEndOfStreamException;
import pl.latusikl.trackit.trackerservice.server.coban.constatns.CobanConstants;
import pl.latusikl.trackit.trackerservice.server.coban.services.CobanConnectionEventService;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TcpServerEventConfiguration
{

    private final CobanConnectionEventService cobanConnectionEventService;
    private final CobanConstants cobanConstants;

    @EventListener
    public void open(final TcpConnectionOpenEvent event)
    {
        if (event.getConnectionFactoryName().equals(cobanConstants.getServerBeanName())) {
            log.debug("Connection id: {}", event.getConnectionId());
            cobanConnectionEventService.handleConnectionOpen(event.getConnectionId());
        }
    }

    //TODO Improve
    @EventListener
    public void exception(final TcpConnectionExceptionEvent exceptionEvent)
    {
        if (exceptionEvent.getCause() instanceof SoftEndOfStreamException) {
            log.debug(exceptionEvent.getCause().getMessage());
        } else {
            log.error(exceptionEvent.getCause().getMessage());
        }
    }

    @EventListener
    public void close(final TcpConnectionCloseEvent event)
    {
        if (event.getConnectionFactoryName().equals(cobanConstants.getServerBeanName())) {
            log.debug("Removing connection with id: {}", event.getConnectionId());
            cobanConnectionEventService.handleConnectionClosed(event.getConnectionId());
        }
    }

}
