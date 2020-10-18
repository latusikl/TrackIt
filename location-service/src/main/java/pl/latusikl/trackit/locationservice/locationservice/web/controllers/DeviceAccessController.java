package pl.latusikl.trackit.locationservice.locationservice.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.latusikl.trackit.locationservice.locationservice.web.service.DeviceAccessService;

@RestController
@RequestMapping("/devices/access")
@RequiredArgsConstructor
public class DeviceAccessController {

	private final DeviceAccessService deviceAccessService;

	@PostMapping("/activate")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void addAccessForDevice(@RequestBody final String deviceId) {
		deviceAccessService.sendActivateAccessRequest(deviceId);
	}

	@PostMapping("/deactivate")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void revokeAccessForDevice(@RequestBody final String deviceId){
		deviceAccessService.sendRevokeAccessRequest(deviceId);
	}
}
