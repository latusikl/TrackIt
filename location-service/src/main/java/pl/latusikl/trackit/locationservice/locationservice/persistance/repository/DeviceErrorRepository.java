package pl.latusikl.trackit.locationservice.locationservice.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.DeviceInfoEntity;

public interface DeviceErrorRepository extends CrudRepository<DeviceInfoEntity, Long> {}
