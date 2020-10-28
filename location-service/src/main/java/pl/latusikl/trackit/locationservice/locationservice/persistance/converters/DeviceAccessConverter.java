package pl.latusikl.trackit.locationservice.locationservice.persistance.converters;

import org.springframework.stereotype.Component;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.DeviceStatus;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.UserData;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.UserDevice;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.DeviceAccessDto;

@Component
public class DeviceAccessConverter {

	public UserDevice convert(final DeviceAccessDto deviceAccessDto, final UserData userData) {
		return UserDevice.builder()
						 .deviceId(deviceAccessDto.getDeviceId())
						 .deviceName(deviceAccessDto.getDeviceName())
						 .deviceStatus(DeviceStatus.ADD_IN_PROGRESS)
						 .userData(userData)
						 .build();
	}

}
