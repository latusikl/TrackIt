package pl.latusikl.trackit.locationservice.locationservice.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LocationDto;
import pl.latusikl.trackit.locationservice.locationservice.web.service.LocationControllerService;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

	private final LocationControllerService locationControllerService;

	@GetMapping("/{deviceId}/last")
	@ResponseStatus(HttpStatus.OK)
	public LocationDto getLastKnownDeviceLocation(@PathVariable final String deviceId) {
		return locationControllerService.getLastKnown(deviceId);
	}

}