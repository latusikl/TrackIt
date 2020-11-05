package pl.latusikl.trackit.locationservice.locationservice.web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.DeviceAccessDto;
import pl.latusikl.trackit.locationservice.locationservice.web.service.DeviceAccessService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/devices/access")
@RequiredArgsConstructor
@Validated
public class DeviceAccessController {

	private final DeviceAccessService deviceAccessService;

	@PostMapping("/{userId}/activate")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void addAccessForDevice(@PathVariable final UUID userId, @RequestBody final DeviceAccessDto deviceAccessDto) {
		deviceAccessService.sendActivateAccessRequest(deviceAccessDto,userId);
	}

	@PostMapping("/{userId}/deactivate")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void revokeAccessForDevice(@PathVariable final UUID userId, @RequestBody final String deviceId) {
		deviceAccessService.sendRevokeAccessRequest(deviceId);
	}
}
