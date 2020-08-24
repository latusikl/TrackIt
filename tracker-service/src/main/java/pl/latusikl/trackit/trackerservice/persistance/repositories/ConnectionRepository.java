package pl.latusikl.trackit.trackerservice.persistance.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.latusikl.trackit.trackerservice.persistance.entities.ConnectionEntity;

import java.util.Optional;

public interface ConnectionRepository
        extends CrudRepository<ConnectionEntity,String>
{
    Optional<ConnectionEntity> findByDeviceImei(String deviceImei);

    void deleteByConnectionId(String connectionId);
}
