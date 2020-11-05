package pl.latusikl.trackit.locationservice.locationservice.persistance.converters;

import org.springframework.stereotype.Component;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.DeviceInfoEntity;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.DeviceInfoDto;

@Component
public class DeviceInfoConverter {

	public DeviceInfoDto convert(final DeviceInfoEntity deviceInfoEntity) {
		return DeviceInfoDto.builder()
							.infoLevel(deviceInfoEntity.getInfoLevel())
							.message(deviceInfoEntity.getMessage())
							.serverDateTime(deviceInfoEntity.getServerDateTime())
							.build();
	}

}
