package pl.latusikl.trackit.locationservice.locationservice.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LastLocationDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LocationRangeDto;
import pl.latusikl.trackit.locationservice.locationservice.web.service.LocationControllerService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

	private final LocationControllerService locationControllerService;

	@GetMapping("/{deviceId}/last")
	@ResponseStatus(HttpStatus.OK)
	public LastLocationDto getLastKnownDeviceLocation(@PathVariable final String deviceId) {
		return locationControllerService.getLastKnown(deviceId);
	}

	@GetMapping("/{deviceId}")
	@ResponseStatus(HttpStatus.OK)
	public LocationRangeDto getFromTimeRange(@PathVariable final String deviceId,
											 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam final LocalDateTime rangeStart,
											 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam final LocalDateTime rangeEnd) {
		return locationControllerService.getFromRange(deviceId, rangeStart, rangeEnd);
	}

}
