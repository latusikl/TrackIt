package pl.latusikl.trackit.locationservice.locationservice.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.latusikl.trackit.locationservice.locationservice.persistance.converters.UserDeviceToUserDeviceDtoConverter;
import pl.latusikl.trackit.locationservice.locationservice.persistance.converters.UserDtoToUserDataConverter;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.UserDataRepository;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.UserDeviceRepository;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.UserDeviceDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.UserDto;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserDeviceRepository userDeviceRepository;
	private final UserDataRepository userDataRepository;
	private final UserDeviceToUserDeviceDtoConverter userDeviceToUserDeviceDtoConverter;
	private final UserDtoToUserDataConverter userDtoToUserDataConverter;

	@Transactional(readOnly = true)
	public Collection<UserDeviceDto> getAllDevicesForUser(final UUID userId) {
		return userDeviceRepository.findAllByUserDataUserId(userId)
								   .stream()
								   .map(userDeviceToUserDeviceDtoConverter::convert)
								   .collect(Collectors.toList());
	}

	@Transactional
	public void addUser(final UserDto userDto) {
		if (userDataRepository.existsByUserEmail(userDto.getUserEmail())) {
			//TODO add exception
			throw new RuntimeException("Change me to specific exception");
		}
		userDataRepository.save(userDtoToUserDataConverter.convert(userDto));
	}

	@Transactional
	public void deleteUserById(final UUID userId) {
		//TODO check if exists
		userDataRepository.deleteByUserId(userId);
	}
}
