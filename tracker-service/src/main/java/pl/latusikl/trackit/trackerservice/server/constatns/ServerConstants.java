package pl.latusikl.trackit.trackerservice.server.constatns;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 Constants for tcp server prepared for GPS trackers.
 */
@Getter
@Component
@NoArgsConstructor
public class ServerConstants {
	private final String locationChannel = "locationChannel";
	private final String otherCommandsChannel = "otherCommandsChannel";
	private final String serverBeanName = "server";
}
