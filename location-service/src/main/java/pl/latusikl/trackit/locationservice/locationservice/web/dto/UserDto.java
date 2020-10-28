package pl.latusikl.trackit.locationservice.locationservice.web.dto;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Value
public class UserDto {

	@NotEmpty
	@Email
	@Length(max = 150)
	private String userEmail;

	@NotEmpty
	@Length(max = 250)
	private String password;

}
