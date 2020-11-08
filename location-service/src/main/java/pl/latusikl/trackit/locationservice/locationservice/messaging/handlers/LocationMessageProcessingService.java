package pl.latusikl.trackit.locationservice.locationservice.messaging.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.location.LocationMessageDto;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.DeviceInfoEntity;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.InfoLevel;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.DeviceInfoRepository;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.LocationRepository;
import pl.latusikl.trackit.locationservice.locationservice.messaging.conversion.LocationMessageDtoConverter;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationMessageProcessingService {

	private final LocationRepository locationRepository;
	private final DeviceInfoRepository deviceErrorRepository;
	private final LocationMessageDtoConverter locationMessageDtoConverter;

	public void persistOrRecordError(final LocationMessageDto locationMessageDto) {
		if (!locationMessageDto.isGpsConnectionStatus()) {
			handleGpsSignalError(locationMessageDto.getId(), locationMessageDto.getDateTime());
		}
		else if (isRecordForGivenTimePersisted(locationMessageDto.getId(), locationMessageDto.getDateTime())) {
			handlePersistenceError(locationMessageDto.getId(), locationMessageDto.getDateTime());
		}
		else {
			locationRepository.save(locationMessageDtoConverter.convert(locationMessageDto));
		}
	}

	private void handleGpsSignalError(final String deviceId, final LocalDateTime dateTime) {
		log.debug("Received location message with low/no gps signal. Persisted device error.");
		final var deviceEntityError = DeviceInfoEntity.builder()
													  .deviceId(deviceId)
													  .serverDateTime(LocalDateTime.now())
													  .message(String.format(
															  "Received location message with low/no gps signal from: %s. Location unknown for this timestamp.",
															  dateTime.toString()))
													  .infoLevel(InfoLevel.WARN)
													  .build();
		deviceErrorRepository.save(deviceEntityError);
	}

	private boolean isRecordForGivenTimePersisted(final String deviceId, final LocalDateTime dateTimeStart) {
		return locationRepository.findByDeviceIdAndAndDateTimeStart(deviceId, dateTimeStart)
								 .isPresent();
	}

	private void handlePersistenceError(final String deviceId, final LocalDateTime dateTimeStart) {
		log.warn(
				"Error when trying to parse location message. Record with given ID and date-time already exists.\n Device ID: {}\n Date:{}",
				deviceId, dateTimeStart);
		final var deviceEntityError = DeviceInfoEntity.builder()
													  .deviceId(deviceId)
													  .serverDateTime(LocalDateTime.now())
													  .message(
															  "Unable to save received location. Location for given timestamp already exists in system.")
													  .infoLevel(InfoLevel.ERROR)
													  .build();
		deviceErrorRepository.save(deviceEntityError);
	}

}
