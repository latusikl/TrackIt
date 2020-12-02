package pl.latusikl.trackit.trackerservice.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MessageChannelConfiguration {

	@Bean
	public MessageChannel locationChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageChannel otherCommandsChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageChannel serverOutChannel() {
		return new DirectChannel();
	}

	@Bean
	public IntegrationFlow locationChannelFlow(final MessageChannel locationChannel,
											   final MessageHandler locationMessageHandler) {
		return IntegrationFlows.from(locationChannel)
							   .handle(locationMessageHandler)
							   .get();
	}

	@Bean
	public IntegrationFlow otherCommandsChannelFlow(final MessageChannel otherCommandsChannel,
													final MessageHandler otherCommandsMessageHandler) {
		return IntegrationFlows.from(otherCommandsChannel)
							   .handle(otherCommandsMessageHandler)
							   .get();
	}
}
