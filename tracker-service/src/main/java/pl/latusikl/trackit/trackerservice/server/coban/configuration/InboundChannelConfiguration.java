package pl.latusikl.trackit.trackerservice.server.coban.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import pl.latusikl.trackit.trackerservice.server.coban.services.LocalizationMessageHandler;
import pl.latusikl.trackit.trackerservice.server.coban.services.OtherCommandsMessageHandler;

@Configuration
public class InboundChannelConfiguration {

	@Bean(name = CobanConstants.LOCALIZATION_CHANNEL)
	MessageChannel serverInChannelOne() {
		return new DirectChannel();
	}

	@Bean(name = CobanConstants.OTHER_COMMAND_CHANNEL)
	MessageChannel serverInChannelTwo() {
		return new DirectChannel();
	}

	@Bean(name = CobanConstants.LOCALIZATION_CHANNEL_HANDLER)
	public MessageHandler serverInChannelOneHandler(final MessageChannel serverOutChannel) {
		return new LocalizationMessageHandler(serverOutChannel);
	}


	@Bean(name = CobanConstants.OTHER_COMMAND_CHANNEL_HANDLER)
	public MessageHandler serverInChannelTwoHandler() {
		return new OtherCommandsMessageHandler();
	}

	@Bean
	MessageChannel serverOutChannel() {
		return new DirectChannel();
	}

	@Bean
	public IntegrationFlow serverInChannelOneFlow(@Qualifier(CobanConstants.LOCALIZATION_CHANNEL) final MessageChannel serverInChannelOne, @Qualifier(CobanConstants.LOCALIZATION_CHANNEL_HANDLER) final MessageHandler serverInChannelOneHandler) {
		return IntegrationFlows.from(serverInChannelOne)
				.handle(serverInChannelOneHandler)
				.get();
	}

	@Bean
	public IntegrationFlow serverInChannelTwoFlow(@Qualifier(CobanConstants.OTHER_COMMAND_CHANNEL) final MessageChannel serverInChannelTwo, @Qualifier(CobanConstants.OTHER_COMMAND_CHANNEL_HANDLER) final MessageHandler serverInChannelTwoHandler) {
		return IntegrationFlows.from(serverInChannelTwo)
				.handle(serverInChannelTwoHandler)
				.get();
	}
}
