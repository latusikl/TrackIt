package pl.latusikl.trackit.locationservice.locationservice.security.models;

import lombok.Value;

@Value
public class LoginDto {

	private String email;
	private String password;

}
