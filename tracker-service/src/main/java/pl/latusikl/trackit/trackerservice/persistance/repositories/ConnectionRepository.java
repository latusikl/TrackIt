package pl.latusikl.trackit.trackerservice.persistance.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.latusikl.trackit.trackerservice.persistance.entities.ConnectionEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ConnectionRepository extends CrudRepository<ConnectionEntity, String> {

	List<ConnectionEntity> findByDeviceImei(String deviceImei);

}
