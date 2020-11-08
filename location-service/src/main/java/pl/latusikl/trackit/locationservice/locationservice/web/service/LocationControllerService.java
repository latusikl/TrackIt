package pl.latusikl.trackit.locationservice.locationservice.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.latusikl.trackit.locationservice.locationservice.exception.LocationNotFoundException;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.LocationEntity;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.LocationRepository;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LastLocationDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LocationDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LocationRangeDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.PointDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson.MapFeatureCollectionDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationControllerService {

	private final LocationRepository locationRepository;
	private final UserToDeviceUtils userToDeviceUtils;
	private final MapFeatureCreator mapFeatureCreator;

	@Transactional(readOnly = true)
	public LastLocationDto getLastKnown(final String deviceId, final UUID userId) {
		userToDeviceUtils.checkIfDeviceOwnedByUserOrElseThrow(deviceId, userId);

		final Optional<LocationEntity> lastKnownLocation = locationRepository.findFirstByDeviceIdOrderByDateTimeStartDesc(deviceId);

		return lastKnownLocation.map(this::mapToLastLocationResponse)
								.orElseThrow(() -> new LocationNotFoundException("Location for device with given ID was not found.",
																				 "No location data was registered so far."));
	}

	private LastLocationDto mapToLastLocationResponse(final LocationEntity locationEntity) {
		final var locationDto = LocationDto.of(locationEntity.getLongitude(), locationEntity.getLatitude(),
											   locationEntity.getDateTimeStart());
		final JsonNode mapPointFeature = mapFeatureCreator.createPoint(locationEntity.getLatitude(), locationEntity.getLongitude());

		final var mapFeatureCollectionDto = new MapFeatureCollectionDto(List.of(mapPointFeature));

		return LastLocationDto.of(locationDto, mapFeatureCollectionDto);
	}

	@Transactional(readOnly = true)
	public LocationRangeDto getFromRange(final String deviceId, final UUID userId, final LocalDateTime rangeStart,
										 final LocalDateTime rangeEnd) {
		userToDeviceUtils.checkIfDeviceOwnedByUserOrElseThrow(deviceId, userId);

		final Collection<LocationEntity> orderedLocationEntitiesFromRange = locationRepository.findInRange(deviceId, rangeStart,
																										   rangeEnd);
		if (orderedLocationEntitiesFromRange.isEmpty()) {
			throw new LocationNotFoundException("None location has been saved in given range",
												"Another possible cause is that no location data was registered so far.");
		}

		return createLocationRangeDto(orderedLocationEntitiesFromRange);
	}

	private LocationRangeDto createLocationRangeDto(final Collection<LocationEntity> orderedLocationEntitiesFromRange) {
		final List<LocationEntity> orderedLocationList = new ArrayList<>(orderedLocationEntitiesFromRange);

		final var firstLocationEntity = orderedLocationList.get(0);
		final var lastLocationEntity = orderedLocationList.get(orderedLocationList.size() - 1);

		final var mapPointFeatureDto = mapFeatureCreator.createPoint(firstLocationEntity.getLatitude(),
																	 firstLocationEntity.getLongitude());
		final var mapFeatureLineString = mapFeatureCreator.createLineString(orderedLocationList.stream()
																							   .map(locationEntity -> PointDto.of(
																									   locationEntity.getLatitude(),
																									   locationEntity.getLongitude()))
																							   .collect(Collectors.toList()));

		return LocationRangeDto.builder()
							   .mapStart(PointDto.of(firstLocationEntity.getLatitude(), firstLocationEntity.getLongitude()))
							   .rangeStart(firstLocationEntity.getDateTimeStart())
							   .rangeEnd(lastLocationEntity.getDateTimeStart())
							   .mapData(new MapFeatureCollectionDto(List.of(mapPointFeatureDto, mapFeatureLineString)))
							   .build();
	}

}
