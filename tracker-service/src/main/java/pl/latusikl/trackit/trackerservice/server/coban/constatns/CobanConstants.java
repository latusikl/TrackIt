package pl.latusikl.trackit.trackerservice.server.coban.constatns;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 Constants for tcp server prepared for Coban GPS trackers.
 */
@Getter
@Component
@NoArgsConstructor
public class CobanConstants {
	private final String locationChannel = "cobanLocationChannel";
	private final String otherCommandsChannel = "cobanOtherCommandsChannel";
	private final String serverBeanName = "cobanServer";
}
