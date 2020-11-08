package pl.latusikl.trackit.locationservice.locationservice.persistance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.LocationEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<LocationEntity, Long> {

	Optional<LocationEntity> findByDeviceIdAndAndDateTimeStart(String deviceId, LocalDateTime dateTimeStart);

	Optional<LocationEntity> findFirstByDeviceIdOrderByDateTimeStartDesc(String deviceId);

	@Query("SELECT location FROM LocationEntity AS location WHERE location.deviceId = ?1 and location.dateTimeStart >= ?2 and location.dateTimeStart < ?3 ORDER BY location.dateTimeStart")
	Collection<LocationEntity> findInRange(String deviceId, LocalDateTime rangeStart, LocalDateTime rangeEnd);
}
