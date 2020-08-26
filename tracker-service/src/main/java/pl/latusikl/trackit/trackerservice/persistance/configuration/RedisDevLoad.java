package pl.latusikl.trackit.trackerservice.persistance.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ConnectionRepository;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ImeiRepository;
import pl.latusikl.trackit.trackerservice.properties.RedisProperties;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Profile("dev")
@Slf4j
public class RedisDevLoad
{
    private final RedisTemplate<String,Object> redisTemplate;

    private final RedisProperties redisProperties;

    private final ConnectionRepository connectionRepository;

    private final ImeiRepository imeiRepository;

    @PostConstruct
    public void loadDevDataToRedis()
    {
        imeiRepository.saveImeiToWhitelisted("864926030089768");
    }
}
