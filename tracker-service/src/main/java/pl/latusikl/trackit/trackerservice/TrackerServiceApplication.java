package pl.latusikl.trackit.trackerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.latusikl.trackit.trackerservice.server.common.ServerProperties;

@SpringBootApplication
@EnableConfigurationProperties(ServerProperties.class)
public class TrackerServiceApplication
{

    public static void main(final String[] args)
    {
        SpringApplication.run(TrackerServiceApplication.class, args);
    }
}
