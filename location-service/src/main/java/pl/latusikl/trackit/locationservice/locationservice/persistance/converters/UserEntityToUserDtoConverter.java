package pl.latusikl.trackit.locationservice.locationservice.persistance.converters;

import org.springframework.stereotype.Component;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.UserData;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.UserDto;

@Component
public class UserEntityToUserDtoConverter {

	public UserDto convert(final UserData userData) {
		return UserDto.builder()
					  .email(userData.getUserEmail())
					  .accountCreation(userData.getAccountCreation())
					  .build();
	}

}
