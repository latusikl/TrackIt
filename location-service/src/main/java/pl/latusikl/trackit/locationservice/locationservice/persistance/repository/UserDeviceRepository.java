package pl.latusikl.trackit.locationservice.locationservice.persistance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.UserDevice;

import java.util.Collection;
import java.util.UUID;

public interface UserDeviceRepository extends PagingAndSortingRepository<UserDevice, String> {

	Collection<UserDevice> findAllByUserDataUserId(UUID userId);

	boolean existsUserDeviceByDeviceIdAndUserDataUserId(String deviceId, UUID userId);

	Integer countAllByUserDataUserId(UUID userId);

	@Query(value = "SELECT COUNT(*) FROM track_it.user_devices", nativeQuery = true)
	Integer countAll();
}
