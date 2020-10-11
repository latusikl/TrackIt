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
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.messaging.MessageChannel;
import pl.latusikl.trackit.trackerservice.properties.ServerProperties;
import pl.latusikl.trackit.trackerservice.server.coban.services.conversion.CustomSerializer;
import pl.latusikl.trackit.trackerservice.server.coban.services.conversion.SemicolonTerminatorDeserializer;

@Slf4j
@Configuration
public class CobanTcpServerConfiguration {

	@Bean
	ObjectToStringTransformer objectToString() {
		return Transformers.objectToString();
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
	 @see TcpConnectionInterceptorFactoryChain
	 */
	@Bean(name = "#{@cobanConstants.getServerBeanName()}")
	AbstractServerConnectionFactory cobanServer(final ServerProperties serverProperties,
												final TcpConnectionInterceptorFactoryChain cobanInterceptorFactoryChain) {
		return Tcp.netServer(serverProperties.getCobanPort())
				  .interceptorFactoryChain(cobanInterceptorFactoryChain)
				  .serializer(new CustomSerializer())
				  .deserializer(new SemicolonTerminatorDeserializer())
				  .get();
	}

	/**
	 Java DSL configuration for inbound message flow from cobanServer. Messages from TcpInboundAdapter are transformed and passed to
	 router. In router appropriate message channel is chosen.
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
