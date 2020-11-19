package pl.latusikl.trackit.trackerservice.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.messaging.OutboundSender;
import pl.latusikl.trackit.trackerservice.persistance.configuration.RedisDevLoad;

import java.util.Arrays;
import java.util.function.Predicate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationReadyHandler implements ApplicationRunner {

	private final LettuceConnectionFactory redisLettuceConnectionFactory;
	private final OutboundSender outboundSender;
	private final RedisDevLoad redisDevLoad;
	private final Environment environment;

	@Override
	public void run(final ApplicationArguments args) throws Exception {
		refreshRedisValuesOnStartup();
		loadAdditionalIfDevProfile();
	}

	public void refreshRedisValuesOnStartup() {
		redisLettuceConnectionFactory.getConnection().flushDb();
		outboundSender.sendAllAllowedDevicesRequest();
		log.info("Clean Redis and send all devices request.");
	}

	private void loadAdditionalIfDevProfile() {
		if (Arrays.stream(environment.getActiveProfiles())
				  .anyMatch(Predicate.isEqual("dev"))) {
			redisDevLoad.loadDevDataToRedis();
		}
	}

}
