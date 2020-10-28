package pl.latusikl.trackit.locationservice.locationservice.web.dto;

import lombok.Builder;
import lombok.Value;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.DeviceStatus;

@Value
@Builder
public class UserDeviceDto {

	private String deviceId;
	private String deviceName;
	private DeviceStatus deviceStatus;

}
