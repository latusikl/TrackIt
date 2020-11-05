package pl.latusikl.trackit.locationservice.locationservice.web.dto;

import lombok.Builder;
import lombok.Value;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.InfoLevel;

import java.time.LocalDateTime;

@Value
@Builder
public class DeviceInfoDto {

	private String message;

	private LocalDateTime serverDateTime;

	private InfoLevel infoLevel;
}
