package pl.latusikl.trackit.locationservice.locationservice.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.LocationEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<LocationEntity, Long> {

	Optional<LocationEntity> findByDeviceIdAndAndDateTimeStart(String deviceId, LocalDateTime dateTimeStart);

	Optional<LocationEntity> findFirstByDeviceIdOrderByDateTimeStartDesc(String deviceId);
}
