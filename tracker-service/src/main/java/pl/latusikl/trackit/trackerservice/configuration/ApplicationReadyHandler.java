package pl.latusikl.trackit.trackerservice.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.messaging.OutboundSender;
import pl.latusikl.trackit.trackerservice.persistance.configuration.RedisDevLoad;

import java.util.Arrays;
import java.util.function.Predicate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationReadyHandler implements ApplicationRunner {

	//6 hours
	private static final long FETCH_INTERVAL = 21_600_000;

	private final LettuceConnectionFactory redisLettuceConnectionFactory;
	private final OutboundSender outboundSender;
//	private final RedisDevLoad redisDevLoad;

	@Override
	public void run(final ApplicationArguments args) {
		executeRefreshProcess();
	}

	@Scheduled(fixedRate = FETCH_INTERVAL)
	private void refreshRedisOnInterval(){
		log.info("Scheduled redis refreshing executed.");
		executeRefreshProcess();
	}

	private void executeRefreshProcess(){
		refreshRedisValuesOnStartup();
//		loadAdditionalIfDevProfile();
	}

	public void refreshRedisValuesOnStartup() {
		redisLettuceConnectionFactory.getConnection().flushDb();
		outboundSender.sendAllAllowedDevicesRequest();
		log.info("Clean Redis and send all devices request.");
	}

//	private void loadAdditionalIfDevProfile() {
//		if (Arrays.stream(environment.getActiveProfiles())
//				  .anyMatch(Predicate.isEqual("dev"))) {
//			redisDevLoad.loadDevDataToRedis();
//		}
//	}
}
