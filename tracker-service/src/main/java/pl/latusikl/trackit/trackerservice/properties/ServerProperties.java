package pl.latusikl.trackit.trackerservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Common Tcp servers properties fetched from application.yaml
 */
@ConfigurationProperties(prefix = "tcp.server")
@Getter
@Setter
public class ServerProperties
{
    private int cobanPort;
}
