package pl.latusikl.trackit.locationservice.locationservice.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.locationservice.locationservice.exception.DeviceNotFoundException;
import pl.latusikl.trackit.locationservice.locationservice.exception.LocationNotFoundException;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.LocationEntity;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.LocationRepository;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LastLocationDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LocationDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson.MapFeatureCollectionDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationControllerService {

	private final LocationRepository locationRepository;
	private final MapFeatureCreator mapFeatureCreator;

	public LastLocationDto getLastKnown(final String deviceId) {
		checkIfDeviceIdInDatabaseOrThrow(deviceId);

		final Optional<LocationEntity> lastKnownLocation = locationRepository.findFirstByDeviceIdOrderByDateTimeStartDesc(deviceId);

		return lastKnownLocation.map(this::mapToLastLocationResponse)
								.orElseThrow(() -> new LocationNotFoundException("Location for device with given ID was not found.",
																				 "No location data was registered so far."));
	}

	private LastLocationDto mapToLastLocationResponse(final LocationEntity locationEntity) {
		final var locationDto = LocationDto.of(locationEntity.getLongitude(), locationEntity.getLatitude(),
											   locationEntity.getDateTimeStart());
		final JsonNode mapPointFeature = mapFeatureCreator.createPoint(locationEntity.getLatitude(), locationEntity.getLongitude());

		final var mapFeatureCollectionDto = MapFeatureCollectionDto.builder()
																   .features(List.of(mapPointFeature))
																   .build();

		return LastLocationDto.of(locationDto, mapFeatureCollectionDto);
	}

	public Collection<LocationDto> getFromRange(final String deviceId, final LocalDateTime rangeStart, final LocalDateTime rangeEnd) {
		checkIfDeviceIdInDatabaseOrThrow(deviceId);

		final Collection<LocationEntity> locationEntitiesFromRange = locationRepository.findAllByDeviceIdAndDateTimeStartBetween(
				deviceId, rangeStart, rangeEnd);
		if (locationEntitiesFromRange.isEmpty()) {
			throw new LocationNotFoundException("None location has been saved in given range",
												"No location data was registered so far.");
		}

		return locationEntitiesFromRange.stream()
										.map(locationEntity -> LocationDto.of(locationEntity.getLongitude(),
																			  locationEntity.getLatitude(),
																			  locationEntity.getDateTimeStart()))
										.collect(Collectors.toList());
	}

	private void checkIfDeviceIdInDatabaseOrThrow(final String deviceId) {
		locationRepository.findFirstByDeviceId(deviceId)
						  .orElseThrow(() -> new DeviceNotFoundException("Device with given ID is not registered."));
	}
}
