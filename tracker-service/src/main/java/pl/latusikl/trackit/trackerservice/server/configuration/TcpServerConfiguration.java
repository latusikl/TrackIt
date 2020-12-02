package pl.latusikl.trackit.trackerservice.server.configuration;

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
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.messaging.MessageChannel;
import pl.latusikl.trackit.trackerservice.properties.ServerProperties;
import pl.latusikl.trackit.trackerservice.server.services.conversion.CustomSerializer;
import pl.latusikl.trackit.trackerservice.server.services.conversion.SemicolonTerminatorDeserializer;

@Slf4j
@Configuration
public class TcpServerConfiguration {

	@Bean
	ObjectToStringTransformer objectToString() {
		return Transformers.objectToString();
	}

	/**
	 Factory chain of server interceptors. In this bean all interceptors should be added to array.
	 */
	@Bean
	TcpConnectionInterceptorFactoryChain interceptorFactoryChain(
			final TcpConnectionInterceptorFactory loginInterceptorFactory) {
		final var interceptorFactoryChain = new TcpConnectionInterceptorFactoryChain();
		interceptorFactoryChain.setInterceptors(new TcpConnectionInterceptorFactory[] {loginInterceptorFactory});
		return interceptorFactoryChain;
	}

	/**
	 Server bean with registered login interceptor and custom serialization/deserialization.

	 @see ServerProperties
	 @see TcpConnectionInterceptorFactoryChain
	 */
	@Bean
	AbstractServerConnectionFactory server(final ServerProperties serverProperties,
										   final TcpConnectionInterceptorFactoryChain interceptorFactoryChain) {
		return Tcp.netServer(serverProperties.getPort())
				  .interceptorFactoryChain(interceptorFactoryChain)
				  .serializer(new CustomSerializer())
				  .deserializer(new SemicolonTerminatorDeserializer())
				  .get();
	}

	/**
	 Java DSL configuration for inbound message flow from server. Messages from TcpInboundAdapter are transformed and passed to
	 router. In router appropriate message channel is chosen.
	 */
	@Bean
	public IntegrationFlow serverIn(final AbstractServerConnectionFactory server, final AbstractMessageRouter serverInRouter) {
		return IntegrationFlows.from(Tcp.inboundAdapter(server))
							   .transform(Transformers.objectToString())
							   .route(serverInRouter)
							   .get();
	}

	/**
	 Java DSL configuration for outbound message flow. Message from outbound channel are passed to server handler.
	 */
	@Bean
	public IntegrationFlow serverOut(final AbstractServerConnectionFactory server, final MessageChannel serverOutChannel) {
		return IntegrationFlows.from(serverOutChannel)
							   .handle(Tcp.outboundAdapter(server))
							   .get();
	}
}
