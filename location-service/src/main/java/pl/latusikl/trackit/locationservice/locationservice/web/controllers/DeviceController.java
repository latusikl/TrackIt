package pl.latusikl.trackit.locationservice.locationservice.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.latusikl.trackit.locationservice.locationservice.security.services.AuthenticationFacade;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.DeviceAccessDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.DeviceInfoDto;
import pl.latusikl.trackit.locationservice.locationservice.web.service.DeviceService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
@Validated
public class DeviceController {

	private final DeviceService deviceService;

	@PostMapping("/access/activate")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void addAccessForDevice(final AuthenticationFacade authenticationFacade,
								   @RequestBody @Valid final DeviceAccessDto deviceAccessDto) {
		deviceService.sendActivateAccessRequest(deviceAccessDto, authenticationFacade.getRequestingUserId());
	}

	@PostMapping("/access/deactivate/{deviceId}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void revokeAccessForDevice(final AuthenticationFacade authenticationFacade, @NotEmpty @PathVariable final String deviceId) {
		deviceService.sendRevokeAccessRequest(authenticationFacade.getRequestingUserId(), deviceId);
	}

	@GetMapping("/{deviceId}/logs")
	@ResponseStatus(HttpStatus.OK)
	public Page<DeviceInfoDto> getDeviceLogs(@NotEmpty @PathVariable final String deviceId,
											 @PageableDefault(size = 15, sort = {"serverDateTime"}) final Pageable pageable,
											 final AuthenticationFacade authenticationFacade) {
		return deviceService.getDeviceLogsPage(pageable, deviceId, authenticationFacade.getRequestingUserId());
	}
}
