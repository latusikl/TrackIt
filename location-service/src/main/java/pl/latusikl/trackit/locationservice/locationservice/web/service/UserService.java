package pl.latusikl.trackit.locationservice.locationservice.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.latusikl.trackit.locationservice.locationservice.exception.UserException;
import pl.latusikl.trackit.locationservice.locationservice.persistance.converters.UserDtoToUserDataConverter;
import pl.latusikl.trackit.locationservice.locationservice.persistance.converters.UserEntityToUserDtoConverter;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.UserDataRepository;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.CreateUserDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.PasswordChangeDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.UserDto;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserDataRepository userDataRepository;
	private final UserDtoToUserDataConverter userDtoToUserDataConverter;
	private final UserEntityToUserDtoConverter userEntityToUserDtoConverter;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void addUser(final CreateUserDto userDto) {
		if (userDataRepository.existsByUserEmail(userDto.getUserEmail())) {
			throw new UserException("User with given email address already exist.");
		}
		userDataRepository.save(userDtoToUserDataConverter.convert(userDto));
	}

	@Transactional
	public void deleteUserById(final UUID userId) {
		checkIfExistsOrElseThrow(userId);
		userDataRepository.deleteByUserId(userId);
	}

	@Transactional(readOnly = true)
	public UserDto getUserInfo(final UUID userId) {
		return userDataRepository.findById(userId)
								 .map(userEntityToUserDtoConverter::convert)
								 .orElseThrow(() -> {
									 log.warn("Tried to access user data for user which doesn't exist. User ID: {}", userId);
									 return new UserException("User was already removed");
								 });
	}

	public void changePassword(final UUID userId, final PasswordChangeDto passwordChangeDto) {
		checkIfExistsOrElseThrow(userId);
		final var userData = userDataRepository.findById(userId)
											   .orElseThrow(() -> {
												   log.error("Authentication error user should not be serched in DB.");
												   return new UserException("User was already removed");
											   });
		userData.setPassword(passwordEncoder.encode(passwordChangeDto.getPassword()));
		userDataRepository.save(userData);
	}

	private void checkIfExistsOrElseThrow(final UUID userId) {
		if (!userDataRepository.existsById(userId)) {
			log.warn("User which not exist in database was authenticated to access removal. User ID: {}", userId);
			throw new UserException("User was already removed");
		}
	}
}
