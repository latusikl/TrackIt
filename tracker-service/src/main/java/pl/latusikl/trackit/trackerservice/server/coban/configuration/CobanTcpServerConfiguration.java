package pl.latusikl.trackit.trackerservice.server.coban.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorFactory;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorFactoryChain;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.MessageChannel;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ConnectionRepository;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ImeiRepository;
import pl.latusikl.trackit.trackerservice.properties.ServerProperties;
import pl.latusikl.trackit.trackerservice.server.coban.CobanConstants;
import pl.latusikl.trackit.trackerservice.server.coban.conversion.MessageSemicolonTerminatorDeserializer;
import pl.latusikl.trackit.trackerservice.server.coban.conversion.MessageSerializer;
import pl.latusikl.trackit.trackerservice.server.coban.interceptors.ConnectionLoginInterceptor;
import pl.latusikl.trackit.trackerservice.server.coban.interceptors.ConnectionLoginInterceptorFactory;
import pl.latusikl.trackit.trackerservice.server.coban.services.InboundMessageRouter;

/**
 Configuration class with tcp server configuration.
 */
@Slf4j
@Configuration
public class CobanTcpServerConfiguration {

	/**
	 Factory for login interceptor.

	 @see ConnectionLoginInterceptorFactory
	 @see ConnectionLoginInterceptor
	 */
	@Bean
	public TcpConnectionInterceptorFactory cobanLoginInterceptorFactory(final ImeiRepository imeiRepository,
																		final ConnectionRepository connectionRepository) {
		return new ConnectionLoginInterceptorFactory(imeiRepository, connectionRepository);
	}

	/**
	 Factory chain of server interceptors. In this bean all interceptors should be added to array.
	 */
	@Bean
	TcpConnectionInterceptorFactoryChain cobanInterceptorFactoryChain(
			final TcpConnectionInterceptorFactory cobanLoginInterceptorFactory) {
		final var interceptorFactoryChain = new TcpConnectionInterceptorFactoryChain();
		interceptorFactoryChain.setInterceptors(new TcpConnectionInterceptorFactory[] {cobanLoginInterceptorFactory});
		return interceptorFactoryChain;
	}

	/**
	 Server bean with registered login interceptor and custom serialization/deserialization.

	 @see ServerProperties
	 */
	@Bean(name = CobanConstants.SERVER_BEAN_NAME)
	AbstractServerConnectionFactory cobanServer(final ServerProperties serverProperties,
												final TcpConnectionInterceptorFactoryChain cobanInterceptorFactoryChain) {
		return Tcp.netServer(serverProperties.getCobanPort())
				  .interceptorFactoryChain(cobanInterceptorFactoryChain)
				  .serializer(new MessageSerializer())
				  .deserializer(new MessageSemicolonTerminatorDeserializer())
				  .get();
	}

	/**
	 Server inbound message router definition.

	 @see InboundMessageRouter
	 */
	@Bean
	public AbstractMessageRouter serverInRouter() {
		return new InboundMessageRouter(Transformers.objectToString());
	}

	/**
	 Java DSL configuration for inbound message flow from cobanServer. Messages are from Tcp InboundAdapter are transformed and passed
	 to router. In router appropriate message channel is choosen.
	 */
	@Bean
	public IntegrationFlow serverIn(final AbstractServerConnectionFactory cobanServer, final AbstractMessageRouter serverInRouter) {
		return IntegrationFlows.from(Tcp.inboundAdapter(cobanServer))
							   .transform(Transformers.objectToString())
							   .route(serverInRouter)
							   .get();
	}

	/**
	 Java DSL configuration for outbound message flow. Message from outbound channel are passed to cobanServer handler.
	 */
	@Bean
	public IntegrationFlow serverOut(final AbstractServerConnectionFactory cobanServer, final MessageChannel cobanServerOutChannel) {
		return IntegrationFlows.from(cobanServerOutChannel)
							   .handle(Tcp.outboundAdapter(cobanServer))
							   .get();
	}
}
