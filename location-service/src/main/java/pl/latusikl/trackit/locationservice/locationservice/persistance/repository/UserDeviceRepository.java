package pl.latusikl.trackit.locationservice.locationservice.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.UserDevice;

import java.util.Collection;
import java.util.UUID;

public interface UserDeviceRepository extends CrudRepository<UserDevice, String> {

	Collection<UserDevice> findAllByUserDataUserId(UUID userId);

	boolean existsUserDeviceByDeviceIdAndUserDataUserId(String deviceId, UUID userId);

}
