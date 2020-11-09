package pl.latusikl.trackit.locationservice.locationservice.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.latusikl.trackit.locationservice.locationservice.exception.UserException;
import pl.latusikl.trackit.locationservice.locationservice.messaging.OutboundSender;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestDto;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestType;
import pl.latusikl.trackit.locationservice.locationservice.persistance.converters.DeviceAccessConverter;
import pl.latusikl.trackit.locationservice.locationservice.persistance.converters.DeviceInfoConverter;
import pl.latusikl.trackit.locationservice.locationservice.persistance.converters.UserDeviceToUserDeviceDtoConverter;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.DeviceInfoRepository;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.UserDataRepository;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.UserDeviceRepository;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.DeviceAccessDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.DeviceInfoDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.UserDeviceDto;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {

	//TODO Lower complexity
	private final UserDeviceRepository userDeviceRepository;
	private final UserDataRepository userDataRepository;
	private final DeviceInfoRepository deviceInfoRepository;

	private final UserToDeviceUtils userToDeviceUtils;
	private final DeviceAccessConverter deviceAccessConverter;
	private final DeviceInfoConverter deviceInfoConverter;
	private final UserDeviceToUserDeviceDtoConverter userDeviceToUserDeviceDtoConverter;

	private final OutboundSender outboundSender;

	@Transactional
	public void sendActivateAccessRequest(final DeviceAccessDto deviceAccessDto, final UUID userId) {
		final var userDevice = deviceAccessConverter.convert(deviceAccessDto, userDataRepository.findById(userId)
																								.orElseThrow(() -> new UserException(
																										"User not found")));
		userDeviceRepository.save(userDevice);
		sendBrokerAccessRequest(deviceAccessDto.getDeviceId(), AccessRequestType.ADD);
	}

	@Transactional(readOnly = true)
	public Collection<UserDeviceDto> getAllDevicesForUser(final UUID userId) {
		return userDeviceRepository.findAllByUserDataUserId(userId)
								   .stream()
								   .map(userDeviceToUserDeviceDtoConverter::convert)
								   .collect(Collectors.toList());
	}

	@Transactional
	public void sendRevokeAccessRequest(final UUID userId, final String deviceId) {
		userToDeviceUtils.checkIfDeviceOwnedByUserOrElseThrow(deviceId, userId);
		userDeviceRepository.deleteById(deviceId);
		sendBrokerAccessRequest(deviceId, AccessRequestType.REMOVE);
	}

	private void sendBrokerAccessRequest(final String deviceId, final AccessRequestType accessRequestType) {
		final var accessRequest = AccessRequestDto.builder()
												  .requestType(accessRequestType)
												  .imei(deviceId)
												  .build();

		outboundSender.sendDeviceAccessRequest(accessRequest);
	}

	@Transactional(readOnly = true)
	public Page<DeviceInfoDto> getDeviceLogsPage(final Pageable pageable, final String deviceId, final UUID userId) {
		userToDeviceUtils.checkIfDeviceOwnedByUserOrElseThrow(deviceId, userId);
		return deviceInfoRepository.findAllByDeviceId(pageable, deviceId)
								   .map(deviceInfoConverter::convert);
	}

	@Transactional(readOnly = true)
	public Integer getUserDevicesCount(final UUID userId) {
		if (!userDataRepository.existsById(userId)) {
			throw new UserException("User not found");
		}
		return userDeviceRepository.countAllByUserDataUserId(userId);

	}
}
