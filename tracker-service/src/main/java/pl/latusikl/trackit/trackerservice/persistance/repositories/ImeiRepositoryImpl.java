package pl.latusikl.trackit.trackerservice.persistance.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.trackerservice.properties.RedisProperties;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ImeiRepositoryImpl
        implements ImeiRepository
{

    private final RedisTemplate<String,Object> redisTemplate;

    private final RedisProperties redisProperties;

    private SetOperations<String,Object> setOperations;

    @PostConstruct
    private void prepareSetOps()
    {
        this.setOperations = redisTemplate.opsForSet();
    }

    @Override
    public void saveImeiToWhitelisted(final String... imei)
    {
        setOperations.add(redisProperties.getImeiSetName(), imei);
    }

    @Override
    public Boolean isImeiWhitelisted(final String imei)
    {
        final Boolean value = setOperations.isMember(redisProperties.getImeiSetName(), imei);
        if (value == null || !value) {
            return false;
        }
        return true;
    }

    @Override
    public void removeImei(final String... imei)
    {
        setOperations.remove(redisProperties.getImeiSetName(), imei);
    }
}
