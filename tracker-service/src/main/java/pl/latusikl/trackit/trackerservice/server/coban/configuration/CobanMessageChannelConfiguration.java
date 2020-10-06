package pl.latusikl.trackit.trackerservice.server.coban.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import pl.latusikl.trackit.trackerservice.server.coban.services.LocalizationMessageHandler;
import pl.latusikl.trackit.trackerservice.server.coban.services.LocalizationMessageParser;
import pl.latusikl.trackit.trackerservice.server.coban.services.OtherCommandsMessageHandler;

@Configuration
public class CobanMessageChannelConfiguration
{

    @Bean
    public MessageChannel cobanLocalizationChannel()
    {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel cobanOtherCommandsChannel()
    {
        return new DirectChannel();
    }

    @Bean
    public MessageHandler cobanLocalizationChannelHandler(final MessageChannel cobanServerOutChannel, final LocalizationMessageParser localizationMessageParser)
    {
        return new LocalizationMessageHandler(cobanServerOutChannel,localizationMessageParser);
    }

    @Bean
    public MessageHandler cobanOtherCommandsChannelHandler()
    {
        return new OtherCommandsMessageHandler();
    }

    @Bean
    public MessageChannel cobanServerOutChannel()
    {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow cobanLocalizationChannelFlow(final MessageChannel cobanLocalizationChannel, final MessageHandler cobanLocalizationChannelHandler)
    {
        return IntegrationFlows.from(cobanLocalizationChannel).handle(cobanLocalizationChannelHandler).get();
    }

    @Bean
    public IntegrationFlow cobanOtherCommandsChannelFlow(final MessageChannel cobanOtherCommandsChannel, final MessageHandler cobanOtherCommandsChannelHandler)
    {
        return IntegrationFlows.from(cobanOtherCommandsChannel).handle(cobanOtherCommandsChannelHandler).get();
    }
}
