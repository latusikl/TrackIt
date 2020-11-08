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
import pl.latusikl.trackit.locationservice.locationservice.security.services.AuthenticationFacade;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LastLocationDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LocationRangeDto;
import pl.latusikl.trackit.locationservice.locationservice.web.service.LocationControllerService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

	private final LocationControllerService locationControllerService;

	@GetMapping("/{deviceId}/last")
	@ResponseStatus(HttpStatus.OK)
	public LastLocationDto getLastKnownDeviceLocation(@NotEmpty @PathVariable final String deviceId,
													  @NotNull final AuthenticationFacade authenticationFacade) {
		return locationControllerService.getLastKnown(deviceId, authenticationFacade.getRequestingUserId());
	}

	@GetMapping("/{deviceId}")
	@ResponseStatus(HttpStatus.OK)
	public LocationRangeDto getFromTimeRange(@NotEmpty @PathVariable final String deviceId,
											 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam final LocalDateTime rangeStart,
											 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam final LocalDateTime rangeEnd,
											 @NotNull final AuthenticationFacade authenticationFacade) {
		return locationControllerService.getFromRange(deviceId, authenticationFacade.getRequestingUserId(), rangeStart, rangeEnd);
	}

}
