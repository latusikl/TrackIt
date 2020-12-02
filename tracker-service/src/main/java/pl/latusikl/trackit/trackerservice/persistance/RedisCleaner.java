package pl.latusikl.trackit.trackerservice.persistance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.messaging.OutboundSender;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCleaner {

	//6 hours
	private static final long FETCH_INTERVAL = 21_600_000;
	private final LettuceConnectionFactory redisLettuceConnectionFactory;
	private final OutboundSender outboundSender;
	//	private final RedisDevLoad redisDevLoad;

	@Scheduled(fixedRate = FETCH_INTERVAL)
	private void refreshRedisOnInterval(){
		log.info("Scheduled redis refreshing executed.");
		executeRefreshProcess();
	}

	public void executeRefreshProcess(){
		refreshRedisValuesOnStartup();
		//		loadAdditionalIfDevProfile();
	}

	private void refreshRedisValuesOnStartup() {
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
