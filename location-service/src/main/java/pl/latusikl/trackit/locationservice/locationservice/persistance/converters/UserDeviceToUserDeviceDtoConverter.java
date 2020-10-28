package pl.latusikl.trackit.locationservice.locationservice.persistance.converters;

import org.springframework.stereotype.Component;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.UserDevice;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.UserDeviceDto;

@Component
public class UserDeviceToUserDeviceDtoConverter {

	public UserDeviceDto convert(final UserDevice userDevice) {
		return UserDeviceDto.builder()
							.deviceId(userDevice.getDeviceId())
							.deviceName(userDevice.getDeviceName())
							.deviceStatus(userDevice.getDeviceStatus())
							.build();
	}

}
