package pl.latusikl.trackit.trackerservice.server.services;

import lombok.RequiredArgsConstructor;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.trackerservice.persistance.entities.ConnectionEntity;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ConnectionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionRemovalService {

	private final ConnectionRepository connectionRepository;
	private final AbstractServerConnectionFactory serverConnectionFactory;

	public void closeConnection(final String deviceId) {
		final List<ConnectionEntity> connectionEntities = connectionRepository.findByDeviceImei(deviceId);
		connectionEntities.stream()
						  .filter(connectionEntity -> connectionEntity.getDeviceImei()
																	  .equals(deviceId))
						  .forEach(connectionEntity -> serverConnectionFactory.closeConnection(connectionEntity.getConnectionId()));
	}

}
