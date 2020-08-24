package pl.latusikl.trackit.trackerservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tcp.server")
@Getter
@Setter
public class ServerProperties
{
    private int cobanPort;
}
