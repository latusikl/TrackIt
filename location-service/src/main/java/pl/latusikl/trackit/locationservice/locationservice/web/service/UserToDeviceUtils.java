package pl.latusikl.trackit.locationservice.locationservice.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.locationservice.locationservice.exception.DeviceNotFoundException;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.UserDeviceRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserToDeviceUtils {

	private final UserDeviceRepository userDeviceRepository;

	public void checkIfDeviceOwnedByUserOrElseThrow(final String deviceId, final UUID userId) {
		if (!userDeviceRepository.existsUserDeviceByDeviceIdAndUserDataUserId(deviceId, userId)) {
			throw new DeviceNotFoundException("Device with given ID is not registered in Your account.");
		}
	}
}
