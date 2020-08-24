package pl.latusikl.trackit.trackerservice.server.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionExceptionEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;
import org.springframework.integration.ip.tcp.serializer.SoftEndOfStreamException;
import pl.latusikl.trackit.trackerservice.properties.CobanConstants;
import pl.latusikl.trackit.trackerservice.server.coban.services.CobanEventService;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TcpServerEventConfiguration
{

    private final CobanEventService cobanEventService;

    @EventListener
    public void open(final TcpConnectionOpenEvent event)
    {
        if (event.getConnectionFactoryName().equals(CobanConstants.COBAN_SERVER_BEAN_NAME)) {
            log.debug("Connection id: {}", event.getConnectionId());
            cobanEventService.handleConnectionOpen(event.getConnectionId());
        }
    }

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
        if (event.getConnectionFactoryName().equals(CobanConstants.COBAN_SERVER_BEAN_NAME)) {
            log.debug("Removing connection with id: {}", event.getConnectionId());
            cobanEventService.handleConnectionClosed(event.getConnectionId());
        }
    }

}
