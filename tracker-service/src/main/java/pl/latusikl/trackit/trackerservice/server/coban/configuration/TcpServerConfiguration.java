package pl.latusikl.trackit.trackerservice.server.coban.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionExceptionEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorFactory;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorFactoryChain;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.messaging.MessageChannel;
import pl.latusikl.trackit.trackerservice.server.coban.CobanConstants;
import pl.latusikl.trackit.trackerservice.server.coban.InboundMessageRouter;
import pl.latusikl.trackit.trackerservice.server.coban.interceptors.CobanConnectionLoginInterceptorFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Configuration
public class TcpServerConfiguration {

	private final Set<String> clients = ConcurrentHashMap.newKeySet();

	@Bean
	CobanConnectionLoginInterceptorFactory loginInterceptorFactory() {
		return new CobanConnectionLoginInterceptorFactory();
	}

	@Bean
	AbstractServerConnectionFactory server(final ServerProperties serverProperties) {
		return Tcp.netServer(serverProperties.getPort()).interceptorFactoryChain(interceptorFactoryChain()).get();
	}

	private TcpConnectionInterceptorFactoryChain interceptorFactoryChain() {
		final TcpConnectionInterceptorFactoryChain interceptorFactoryChain = new TcpConnectionInterceptorFactoryChain();
		final var TcpConnectionInterceptorFactories = new TcpConnectionInterceptorFactory[1];
		TcpConnectionInterceptorFactories[0] = loginInterceptorFactory();
		interceptorFactoryChain.setInterceptors(TcpConnectionInterceptorFactories);

		return interceptorFactoryChain;
	}

	@Bean
	public AbstractMessageRouter serverRouter() {
		return new InboundMessageRouter(new ObjectToStringTransformer());
	}

	@Bean
	public IntegrationFlow serverIn(final AbstractServerConnectionFactory server) {
		return IntegrationFlows.from(Tcp.inboundAdapter(server))
				.transform(Transformers.objectToString())
				.log(msg -> "server: " + msg.getPayload())
				.route(serverRouter())
				.get();
	}

	@Bean(name = CobanConstants.LOCALIZATION_CHANNEL)
	MessageChannel serverInChannel1() {
		return new DirectChannel();
	}

	@Bean(name = CobanConstants.COMMAND_CHANNEL)
	MessageChannel serverInChannel2() {
		return new DirectChannel();
	}


	@Bean
	public IntegrationFlow serverOut(final AbstractServerConnectionFactory server) {
		return IntegrationFlows.from(() -> "seed", e -> e.poller(Pollers.fixedDelay(5000)))
				.split(this.clients, "iterator")
				.enrichHeaders(h -> h.headerExpression(IpHeaders.CONNECTION_ID, "payload"))
				.transform(p -> "Hello from server")
				.handle(Tcp.outboundAdapter(server))
				.get();
	}

	@EventListener
	public void open(final TcpConnectionOpenEvent event) {
		if (event.getConnectionFactoryName().equals("server")) {
			this.clients.add(event.getConnectionId());
		}
	}

	@EventListener
	public void exception(final TcpConnectionExceptionEvent exceptionEvent) {

	}

	@EventListener
	public void close(final TcpConnectionCloseEvent event) {
		this.clients.remove(event.getConnectionId());
	}
}
