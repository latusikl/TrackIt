package pl.latusikl.trackit.locationservice.locationservice.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.locationservice.locationservice.exception.LocationNotFoundException;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.LocationEntity;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.LocationRepository;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LocationDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationControllerService {

	private final LocationRepository locationRepository;

	public LocationDto getLastKnown(final String deviceId) {
		final Optional<LocationEntity> lastKnownLocation = locationRepository.findFirstByDeviceIdOrderByDateTimeStartDesc(deviceId);
		return lastKnownLocation.map(locationEntity -> LocationDto.of(locationEntity.getLongitude(), locationEntity.getLatitude(),
																	  locationEntity.getDateTimeStart()))
								.orElseThrow(() -> new LocationNotFoundException("Location for device with given ID was not found.",
																				 "No location data was registered so far."));
	}
}
