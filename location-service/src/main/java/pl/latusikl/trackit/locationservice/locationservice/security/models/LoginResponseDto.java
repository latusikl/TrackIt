package pl.latusikl.trackit.locationservice.locationservice.security.models;

import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class LoginResponseDto {

	private LocalDateTime expires;
	private String token;

}
