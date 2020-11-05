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
import pl.latusikl.trackit.locationservice.locationservice.web.dto.DeviceAccessDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.DeviceInfoDto;
import pl.latusikl.trackit.locationservice.locationservice.web.service.DeviceService;

import java.util.UUID;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
@Validated
public class DeviceController {

	private final DeviceService deviceService;

	@PostMapping("/access/{userId}/activate")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void addAccessForDevice(@PathVariable final UUID userId, @RequestBody final DeviceAccessDto deviceAccessDto) {
		deviceService.sendActivateAccessRequest(deviceAccessDto, userId);
	}

	@PostMapping("/access/{userId}/deactivate")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void revokeAccessForDevice(@PathVariable final UUID userId, @RequestBody final String deviceId) {
		deviceService.sendRevokeAccessRequest(deviceId);
	}

	@GetMapping("/{deviceId}/logs")
	@ResponseStatus(HttpStatus.OK)
	public Page<DeviceInfoDto> getDeviceLogs(@PageableDefault(size = 15, sort = {"serverDateTime"}) final Pageable pageable) {
		return deviceService.getDeviceLogsPage(pageable);
	}
}
