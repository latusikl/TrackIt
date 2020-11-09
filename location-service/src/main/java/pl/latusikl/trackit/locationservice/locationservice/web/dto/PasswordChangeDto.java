package pl.latusikl.trackit.locationservice.locationservice.web.dto;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Value
public class PasswordChangeDto {
	@NotEmpty
	@Length(max = 250, min = 8)
	private String password;
}
