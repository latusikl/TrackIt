package pl.latusikl.trackit.locationservice.locationservice.messaging.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestCallbackDto;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestStatus;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestType;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.DeviceStatus;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.UserDevice;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.UserDeviceRepository;
import pl.latusikl.trackit.locationservice.locationservice.web.service.DeviceInfoMessageService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationCallbackProcessingService {

	private static final String PROCESS_MESSAGE_TEMPLATE = "Process finished. Result: %s";
	private final DeviceInfoMessageService deviceInfoMessageService;
	private final UserDeviceRepository userDeviceRepository;

	public void handle(final AccessRequestCallbackDto callbackDto) {
		if (callbackDto.getAccessRequestType() == AccessRequestType.ADD_SINGLE) {
			determineMessageTypeAndSaveInfo(callbackDto);
			updateDeviceStatus(callbackDto);
		}
		else if (callbackDto.getAccessRequestType() == AccessRequestType.REMOVE) {
			determineMessageTypeAndSaveInfo(callbackDto);
		}
	}

	private void updateDeviceStatus(final AccessRequestCallbackDto callbackDto) {
		if (callbackDto.getAccessRequestStatus() == AccessRequestStatus.FINISHED) {
			saveNewDeviceStatus(callbackDto.getDeviceId(), DeviceStatus.CONNECTED);
		}
		else if (callbackDto.getAccessRequestStatus() == AccessRequestStatus.ERROR) {
			saveNewDeviceStatus(callbackDto.getDeviceId(), DeviceStatus.CONNECTION_ERROR);
		}
	}

	private void saveNewDeviceStatus(final String deviceId, final DeviceStatus deviceStatus) {
		final Optional<UserDevice> userDeviceOptional = userDeviceRepository.findById(deviceId);
		if (userDeviceOptional.isPresent()) {
			final var userDevice = userDeviceOptional.get();
			userDevice.setDeviceStatus(deviceStatus);
			userDeviceRepository.save(userDevice);
		}
		else {
			throw new IllegalStateException("Unable to find device with ID: " + deviceId);
		}
	}

	private void determineMessageTypeAndSaveInfo(final AccessRequestCallbackDto callbackDto) {
		if (callbackDto.getAccessRequestStatus() == AccessRequestStatus.FINISHED) {
			deviceInfoMessageService.saveInfoMessage(callbackDto.getDeviceId(),
													 String.format(PROCESS_MESSAGE_TEMPLATE, callbackDto.getRequestInformation()));
		}
		else if (callbackDto.getAccessRequestStatus() == AccessRequestStatus.ERROR) {
			deviceInfoMessageService.saveErrorMessage(callbackDto.getDeviceId(),
													  String.format(PROCESS_MESSAGE_TEMPLATE, callbackDto.getRequestInformation()));
		}
	}

}
