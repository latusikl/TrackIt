package pl.latusikl.trackit.locationservice.locationservice.persistance.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.UserData;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.CreateUserDto;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserDtoToUserDataConverter {

	private final PasswordEncoder passwordEncoder;

	public UserData convert(final CreateUserDto userDto) {
		return UserData.builder()
					   .userEmail(userDto.getUserEmail())
					   .password(passwordEncoder.encode(userDto.getPassword()))
					   .accountCreation(LocalDateTime.now())
					   .build();
	}

}
