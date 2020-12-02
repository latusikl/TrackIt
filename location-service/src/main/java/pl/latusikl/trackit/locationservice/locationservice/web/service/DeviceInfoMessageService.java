package pl.latusikl.trackit.locationservice.locationservice.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.DeviceInfoEntity;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.InfoLevel;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.DeviceInfoRepository;

@Service
@RequiredArgsConstructor
public class DeviceInfoMessageService {

	private final DeviceInfoRepository deviceInfoRepository;
	private final TimeUtcUtils timeUtcUtils;

	public void saveInfoMessage(final String deviceId, final String message) {
		addInternal(deviceId, message, InfoLevel.INFO);
	}

	public void saveWarnMessage(final String deviceId, final String message) {
		addInternal(deviceId, message, InfoLevel.WARN);
	}

	public void saveErrorMessage(final String deviceId, final String message) {
		addInternal(deviceId, message, InfoLevel.ERROR);
	}

	private void addInternal(final String deviceId, final String message, final InfoLevel infoLevel) {
		final var deviceInfoEntity = DeviceInfoEntity.builder()
													 .deviceId(deviceId)
													 .serverDateTime(timeUtcUtils.getUtcNow())
													 .message(
															 message)
													 .infoLevel(infoLevel)
													 .build();
		deviceInfoRepository.save(deviceInfoEntity);
	}

}
