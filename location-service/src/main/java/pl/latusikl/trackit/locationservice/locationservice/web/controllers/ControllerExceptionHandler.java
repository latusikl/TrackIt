package pl.latusikl.trackit.locationservice.locationservice.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.latusikl.trackit.locationservice.locationservice.exception.LocationNotFoundException;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.ExceptionResponseDto;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(LocationNotFoundException.class)
	public ResponseEntity<ExceptionResponseDto> handleLocationNotFound(final LocationNotFoundException exception) {
		return ResponseEntity.status(exception.getHttpStatus())
							 .body(ExceptionResponseDto.of(exception.getMessage(), exception.getAdditionalInformation(),
														   LocalDateTime.now()));
	}

}
