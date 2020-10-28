package pl.latusikl.trackit.locationservice.locationservice.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.UserDeviceDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.UserDto;
import pl.latusikl.trackit.locationservice.locationservice.web.service.UserService;

import java.util.Collection;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/{userId}/devices")
	@ResponseStatus(HttpStatus.OK)
	Collection<UserDeviceDto> getAllUserDevices(@PathVariable final UUID userId) {
		return userService.getAllDevicesForUser(userId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void addUser(@RequestBody final UserDto userDto) {
		userService.addUser(userDto);

	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable final UUID userId) {
		userService.deleteUserById(userId);
	}

}
