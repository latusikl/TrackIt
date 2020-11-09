package pl.latusikl.trackit.locationservice.locationservice.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.latusikl.trackit.locationservice.locationservice.security.services.AuthenticationFacade;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.CreateUserDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.UserDeviceDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.UserDto;
import pl.latusikl.trackit.locationservice.locationservice.web.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@Validated
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void addUser(@RequestBody @Valid final CreateUserDto userDto) {
		userService.addUser(userDto);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@NotNull final AuthenticationFacade authenticationFacade) {
		userService.deleteUserById(authenticationFacade.getRequestingUserId());
	}

	@GetMapping("/me")
	@ResponseStatus(HttpStatus.OK)
	public UserDto getUserInfo(@NotNull final AuthenticationFacade authenticationFacade) {
		return userService.getUserInfo(authenticationFacade.getRequestingUserId());
	}
}
