package pl.latusikl.trackit.trackerservice.server.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("tcp.server")
@Getter
@Setter
public class ServerProperties
{

    private int cobanPort;
}
