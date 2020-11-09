package pl.latusikl.trackit.locationservice.locationservice.web.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class UserDto {

	private String email;
	private LocalDateTime accountCreation;

}
