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
import pl.latusikl.trackit.trackerservice.persistance.RedisCleaner;
import pl.latusikl.trackit.trackerservice.persistance.configuration.RedisDevLoad;

import java.util.Arrays;
import java.util.function.Predicate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationReadyHandler implements ApplicationRunner {

	private final RedisCleaner redisCleaner;

	@Override
	public void run(final ApplicationArguments args) {
		redisCleaner.executeRefreshProcess();
	}

}
