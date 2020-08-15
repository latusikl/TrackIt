package pl.latusikl.trackit.trackerservice.server.coban.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionExceptionEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;


import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Configuration
public class TcpServerConfiguration {

	private final Set<String> clients = ConcurrentHashMap.newKeySet();

	@Bean
	AbstractServerConnectionFactory server(final ServerProperties serverProperties) {
		return Tcp.netServer(serverProperties.getPort()).get();
	}


	@Bean
	public IntegrationFlow serverIn(AbstractServerConnectionFactory server) {
		return IntegrationFlows.from(Tcp.inboundAdapter(server))
				.transform(Transformers.objectToString())
				.log(msg -> "server: " + msg.getPayload())
				.get();
	}

	@Bean
	public IntegrationFlow serverOut(AbstractServerConnectionFactory server) {
		return IntegrationFlows.from(() -> "seed", e -> e.poller(Pollers.fixedDelay(5000)))
				.split(this.clients, "iterator")
				.enrichHeaders(h -> h.headerExpression(IpHeaders.CONNECTION_ID, "payload"))
				.transform(p -> "Hello from server")
				.handle(Tcp.outboundAdapter(server))
				.get();
	}

	@EventListener
	public void open(TcpConnectionOpenEvent event) {
		if (event.getConnectionFactoryName().equals("server")) {
			this.clients.add(event.getConnectionId());
		}
	}

	@EventListener
	public void exception(TcpConnectionExceptionEvent exceptionEvent){

	}

	@EventListener
	public void close(TcpConnectionCloseEvent event) {
		this.clients.remove(event.getConnectionId());
	}
}
