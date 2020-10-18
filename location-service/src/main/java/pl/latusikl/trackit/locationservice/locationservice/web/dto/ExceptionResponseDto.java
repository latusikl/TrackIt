package pl.latusikl.trackit.locationservice.locationservice.web.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class ExceptionResponseDto {

	private String message;
	private String additionalInformation;
	private LocalDateTime serverTime;
}
