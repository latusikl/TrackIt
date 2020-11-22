package pl.latusikl.trackit.locationservice.locationservice.persistance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.LocationEntity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<LocationEntity, Long> {

	Optional<LocationEntity> findByDeviceIdAndAndDateTimeStart(String deviceId, LocalDateTime dateTimeStart);

	Optional<LocationEntity> findFirstByDeviceIdOrderByDateTimeStartDesc(String deviceId);

	@Query(value = "SELECT DISTINCT date(ld.date_time_start) FROM track_it.location_data AS ld WHERE ld.date_time_start >= to_date(:start,'YYYYMM') AND ld.date_time_start < to_date(:end,'YYYYMM') AND ld.device_id=:id", nativeQuery = true)
	Collection<Date> findDatesWhereLocationInMonth(@Param("start") String startYearMonth, @Param("end") String endYearMonth,
												   @Param("id") String deviceId);

	@Query("SELECT location FROM LocationEntity AS location WHERE location.deviceId = ?1 and location.dateTimeStart >= ?2 and location.dateTimeStart < ?3 ORDER BY location.dateTimeStart")
	Collection<LocationEntity> findInRange(String deviceId, LocalDateTime rangeStart, LocalDateTime rangeEnd);
}
