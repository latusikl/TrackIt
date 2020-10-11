package pl.latusikl.trackit.trackerservice.server.coban.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class CobanMessageChannelConfiguration {

	@Bean
	public MessageChannel cobanLocationChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageChannel cobanOtherCommandsChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageChannel cobanServerOutChannel() {
		return new DirectChannel();
	}

	@Bean
	public IntegrationFlow cobanLocalizationChannelFlow(final MessageChannel cobanLocationChannel,
														final MessageHandler cobanLocationMessageHandler) {
		return IntegrationFlows.from(cobanLocationChannel)
							   .handle(cobanLocationMessageHandler)
							   .get();
	}

	@Bean
	public IntegrationFlow cobanOtherCommandsChannelFlow(final MessageChannel cobanOtherCommandsChannel,
														 final MessageHandler cobanOtherCommandsMessageHandler) {
		return IntegrationFlows.from(cobanOtherCommandsChannel)
							   .handle(cobanOtherCommandsMessageHandler)
							   .get();
	}
}
