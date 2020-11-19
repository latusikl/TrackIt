package pl.latusikl.trackit.trackerservice.persistance.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.messaging.OutboundProcessor;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ConnectionRepository;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ImeiRepository;
import pl.latusikl.trackit.trackerservice.properties.RedisProperties;

import javax.annotation.PostConstruct;

/**
 Allows to inject values to Redis Container during service startup. Only for development purposes.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisDevLoad {
	private final RedisTemplate<String, Object> redisTemplate;

	private final RedisProperties redisProperties;

	private final ConnectionRepository connectionRepository;

	private final ImeiRepository imeiRepository;

	private final OutboundProcessor locationSource;

	public void loadDevDataToRedis() {
		log.warn("This component should be active only in development environment.");
		log.debug(imeiRepository.saveImeiToWhitelisted("864926030089768").toString());
	}
}
