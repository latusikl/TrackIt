package pl.latusikl.trackit.locationservice.locationservice.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.latusikl.trackit.locationservice.locationservice.exception.LocationNotFoundException;
import pl.latusikl.trackit.locationservice.locationservice.exception.MalformedRequestException;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.LocationEntity;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.LocationRepository;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LastLocationDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LocationDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LocationRangeDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.MonthYearDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.PointDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.TrackDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson.MapFeatureCollectionDto;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationControllerService {

	private final LocationRepository locationRepository;
	private final MapFeatureCreator mapFeatureCreator;
	private final DeviceInfoMessageService deviceInfoMessageService;
	private final TrackGeneratorService trackGeneratorService;

	@Transactional(readOnly = true)
	public LastLocationDto getLastKnown(final String deviceId) {
		final Optional<LocationEntity> lastKnownLocation = locationRepository.findFirstByDeviceIdOrderByDateTimeStartDesc(deviceId);

		return lastKnownLocation.map(this::mapToLastLocationResponse)
								.orElseThrow(() -> {
									deviceInfoMessageService.saveInfoMessage(deviceId,
																			 "Unable to return last device location. No location data was registered so far.");
									return new LocationNotFoundException("Location for device with given ID was not found.",
																		 "No location data was registered so far.");
								});
	}

	private LastLocationDto mapToLastLocationResponse(final LocationEntity locationEntity) {
		final var locationDto = LocationDto.of(locationEntity.getLocation()
															 .getX(), locationEntity.getLocation()
																					.getY(), locationEntity.getDateTimeStart());
		final JsonNode mapPointFeature = mapFeatureCreator.createPoint(locationEntity.getLocation()
																					 .getX(), locationEntity.getLocation()
																											.getY());

		final var mapFeatureCollectionDto = new MapFeatureCollectionDto(List.of(mapPointFeature));

		return LastLocationDto.of(locationDto, mapFeatureCollectionDto);
	}

	@Transactional(readOnly = true)
	public LocationRangeDto getFromRange(final String deviceId, final LocalDateTime rangeStart, final LocalDateTime rangeEnd) {

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

		final var mapPointFeatureDto = mapFeatureCreator.createPoint(firstLocationEntity.getLocation()
																						.getX(), firstLocationEntity.getLocation()
																													.getY());
		final var mapFeatureLineString = mapFeatureCreator.createLineString(orderedLocationList.stream()
																							   .map(locationEntity -> PointDto.of(
																									   locationEntity.getLocation()
																													 .getX(),
																									   locationEntity.getLocation()
																													 .getY()))
																							   .collect(Collectors.toList()));

		return LocationRangeDto.builder()
							   .mapStart(PointDto.of(firstLocationEntity.getLocation()
																		.getX(), firstLocationEntity.getLocation()
																									.getY()))
							   .rangeStart(firstLocationEntity.getDateTimeStart())
							   .rangeEnd(lastLocationEntity.getDateTimeStart())
							   .mapData(new MapFeatureCollectionDto(List.of(mapPointFeatureDto, mapFeatureLineString)))
							   .build();
	}

	@Transactional(readOnly = true)
	public Collection<LocalDate> findAllDatesWithLocationInMonth(final String deviceId, final MonthYearDto monthYearDto) {
		final var yearString = String.valueOf(monthYearDto.getYear());
		final var startYearMonth = yearString + monthYearDto.getMonth();
		final var endYearMonth = yearString + (monthYearDto.getMonth() + 1);

		return locationRepository.findDatesWhereLocationInMonth(startYearMonth, endYearMonth, deviceId)
								 .stream()
								 .map(Date::toLocalDate)
								 .collect(Collectors.toUnmodifiableList());
	}

	public Collection<TrackDto> getTracks(final String deviceId, final LocalDateTime rangeStart, final LocalDateTime rangeEnd) {
		return trackGeneratorService.generateTracks(deviceId, rangeStart, rangeEnd);
	}

	public void checkIfRangeNotToBigOrThrow(final LocalDateTime rangeStart, final LocalDateTime rangeEnd, final int limit) {
		if (!rangeEnd.isBefore(rangeStart.plusHours(limit)
										 .plusSeconds(1))) {
			throw new MalformedRequestException("Date range limit was exceeded", "Maximum range size is: " + limit + "h");
		}
	}
}
