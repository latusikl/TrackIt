package pl.latusikl.trackit.locationservice.locationservice.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.LocationEntity;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.LocationRepository;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.TrackDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackGeneratorService {

	private static final int MAX_HOURS_PER_TRACK = 1;
	private static final int MAX_HOURS_PER_OPERATION = 2;
	private static final int MAX_MINUTE_BREAK = 10;
	private static final String TRACK_NAME = "track_";

	private final LocationRepository locationRepository;

	@Transactional(readOnly = true)
	public Collection<TrackDto> generateTracks(final String deviceId, final LocalDateTime rangeStart, final LocalDateTime rangeEnd) {

		throwIfRangeExceeded(rangeStart, rangeEnd);

		final List<LocationEntity> locationEntities = locationRepository.findInRange(deviceId, rangeStart, rangeEnd);

		if (locationEntities.isEmpty()) {
			return Collections.emptyList();
		}

		if (locationEntities.size() == 1) {
			final var singleLocationDateTime = locationEntities.get(0)
															   .getDateTimeStart();
			return List.of(TrackDto.of(singleLocationDateTime, singleLocationDateTime, TRACK_NAME + 1));
		}

		final List<TrackDto> trackDtos = new ArrayList<>();
		TrackDto lastTrack = TrackDto.of(locationEntities.get(0)
														 .getDateTimeStart(), null, TRACK_NAME + 1);
		final var locationListSize = locationEntities.size();

		for (int i = 1; i < locationListSize; i++) {
			final var previousLocationDateTime = locationEntities.get(i - 1)
																 .getDateTimeStart();
			final var locationDateTime = locationEntities.get(i)
														 .getDateTimeStart();

			if (i + 1 == locationListSize) {
				lastTrack.setEnd(locationDateTime);
				trackDtos.add(lastTrack);
			}
			else if (lastTrack.getStart()
							  .plusHours(MAX_HOURS_PER_TRACK)
							  .isBefore(locationDateTime) || locationDateTime.isAfter(
					previousLocationDateTime.plusMinutes(MAX_MINUTE_BREAK))) {
				lastTrack.setEnd(previousLocationDateTime);
				trackDtos.add(lastTrack);
				lastTrack = TrackDto.of(locationDateTime, null, TRACK_NAME + (trackDtos.size() + 1));
			}

		}
		return trackDtos;
	}

	private void throwIfRangeExceeded(final LocalDateTime rangeStart, final LocalDateTime rangeEnd) {
		if (rangeStart.plusHours(MAX_HOURS_PER_OPERATION)
					  .isBefore(rangeEnd)) {
			throw new IllegalStateException("To big range passed to service");
		}
	}

}

