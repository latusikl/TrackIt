package pl.latusikl.trackit.locationservice.locationservice.persistance.converters;

import org.springframework.stereotype.Component;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.UserData;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.UserDto;

@Component
public class UserDtoToUserDataConverter {

	public UserData convert(final UserDto userDto) {
		return UserData.builder()
					   .userEmail(userDto.getUserEmail())
					   .password(userDto.getPassword())
					   .build();
	}

}
