package pl.latusikl.trackit.trackerservice.server.coban.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionExceptionEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;


@Slf4j
@Configuration
public class TcpServerEventConfiguration {

	@EventListener
	public void open(final TcpConnectionOpenEvent event) {
		if (event.getConnectionFactoryName().equals("server")) {
			log.info("Connection id: {}", event.getConnectionId());
		}
	}

	@EventListener
	public void exception(final TcpConnectionExceptionEvent exceptionEvent) {
		log.error(exceptionEvent.getCause().getMessage());
	}

	@EventListener
	public void close(final TcpConnectionCloseEvent event) {
		log.info("Removed connection id: {}", event.getConnectionId());
	}

}
