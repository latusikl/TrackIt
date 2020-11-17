package pl.latusikl.trackit.locationservice.locationservice.web.dto;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Value(staticConstructor = "of")
public class DeviceAccessDto {

	@NotEmpty
	@Length(min = 1, max = 145)
	private String deviceId;

	@NotEmpty
	@Length(min = 1, max = 80)
	private String deviceName;
}

