package pl.latusikl.trackit.locationservice.locationservice.persistance.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.DeviceInfoEntity;

public interface DeviceInfoRepository extends PagingAndSortingRepository<DeviceInfoEntity, Long> {}
