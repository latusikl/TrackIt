package pl.latusikl.trackit.locationservice.locationservice.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.latusikl.trackit.locationservice.locationservice.security.services.AuthenticationFacade;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LastLocationDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LocationRangeDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.MonthYearDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.TrackDto;
import pl.latusikl.trackit.locationservice.locationservice.web.service.LocationControllerService;
import pl.latusikl.trackit.locationservice.locationservice.web.service.UserToDeviceUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@Validated
public class LocationController {

	private static final int MAX_RANGE_HOURS_TRACKS = 2;
	private static final int MAX_RANGE_HOURS_LOCATION = 1;
	private final LocationControllerService locationControllerService;
	private final UserToDeviceUtils userToDeviceUtils;

	@GetMapping("/{deviceId}/last")
	@ResponseStatus(HttpStatus.OK)
	public LastLocationDto getLastKnownDeviceLocation(@NotEmpty @PathVariable final String deviceId,
													  @NotNull final AuthenticationFacade authenticationFacade) {
		userToDeviceUtils.checkIfDeviceOwnedByUserOrElseThrow(deviceId, authenticationFacade.getRequestingUserId());
		return locationControllerService.getLastKnown(deviceId);
	}

	@GetMapping("/{deviceId}")
	@ResponseStatus(HttpStatus.OK)
	public LocationRangeDto getFromTimeRange(@NotEmpty @PathVariable final String deviceId,
											 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam final LocalDateTime rangeStart,
											 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam final LocalDateTime rangeEnd,
											 @NotNull final AuthenticationFacade authenticationFacade) {
		userToDeviceUtils.checkIfDeviceOwnedByUserOrElseThrow(deviceId, authenticationFacade.getRequestingUserId());
		locationControllerService.checkIfRangeNotToBigOrThrow(rangeStart, rangeEnd, MAX_RANGE_HOURS_LOCATION);
		return locationControllerService.getFromRange(deviceId, rangeStart, rangeEnd);
	}

	@GetMapping("/{deviceId}/dates")
	public Collection<LocalDate> getDaysWithLocationInYearMonth(@NotEmpty @PathVariable final String deviceId,
																@Valid final MonthYearDto monthYearDto,
																@NotNull final AuthenticationFacade authenticationFacade) {
		userToDeviceUtils.checkIfDeviceOwnedByUserOrElseThrow(deviceId, authenticationFacade.getRequestingUserId());
		return locationControllerService.findAllDatesWithLocationInMonth(deviceId, monthYearDto);
	}

	@GetMapping("/{deviceId}/tracks")
	public Collection<TrackDto> getTracksForGivenPeriod(@NotEmpty @PathVariable final String deviceId,
														@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam final LocalDateTime rangeStart,
														@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam final LocalDateTime rangeEnd,
														@NotNull final AuthenticationFacade authenticationFacade) {
		userToDeviceUtils.checkIfDeviceOwnedByUserOrElseThrow(deviceId, authenticationFacade.getRequestingUserId());
		locationControllerService.checkIfRangeNotToBigOrThrow(rangeStart, rangeEnd, MAX_RANGE_HOURS_TRACKS);
		return locationControllerService.getTracks(deviceId, rangeStart, rangeEnd);
	}

}
