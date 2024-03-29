package pl.latusikl.trackit.trackerservice.server.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.trackerservice.persistance.entities.ConnectionEntity;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ConnectionRepository;
import pl.latusikl.trackit.trackerservice.properties.ServiceConstants;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionEventService {
	private final ConnectionRepository connectionRepository;

	public void handleConnectionOpen(final String conenctionId) {
		connectionRepository.save(ConnectionEntity.builder()
												  .connectionId(conenctionId)
												  .deviceImei(ServiceConstants.EMPTY_STRING)
												  .build());
	}

	public void handleConnectionClosed(final String connectionId) {
		log.debug("Removing connection with id: {}", connectionId);
		connectionRepository.deleteById(connectionId);
	}
}
