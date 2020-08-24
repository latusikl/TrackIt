package pl.latusikl.trackit.trackerservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Common Redis properties fetched from application.yaml
 */
@ConfigurationProperties(prefix = "redis")
@Getter
@Setter
public class RedisProperties
{
    private String imeiSetName;
}
