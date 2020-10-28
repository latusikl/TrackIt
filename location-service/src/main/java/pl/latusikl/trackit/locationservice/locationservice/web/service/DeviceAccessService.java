package pl.latusikl.trackit.locationservice.locationservice.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.latusikl.trackit.locationservice.locationservice.messaging.OutboundSender;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestDto;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestType;
import pl.latusikl.trackit.locationservice.locationservice.persistance.converters.DeviceAccessConverter;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.UserDataRepository;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.UserDeviceRepository;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.DeviceAccessDto;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceAccessService {

	private final UserDeviceRepository userDevicesRepository;
	private final UserDataRepository userDataRepository;
	private final DeviceAccessConverter deviceAccessConverter;
	private final OutboundSender outboundSender;

	@Transactional
	public void sendActivateAccessRequest(final DeviceAccessDto deviceAccessDto, final UUID userId) {
		final var userDevice = deviceAccessConverter.convert(deviceAccessDto, userDataRepository.findById(userId)
																								.orElseThrow(
																										() -> new RuntimeException(
																												"Change me to custom exception")));
		userDevicesRepository.save(userDevice);

		sendBrokerAccessRequest(deviceAccessDto.getDeviceId(), AccessRequestType.ADD);
	}

	@Transactional
	public void sendRevokeAccessRequest(final String deviceId) {
		userDevicesRepository.deleteById(deviceId);
		sendBrokerAccessRequest(deviceId, AccessRequestType.REMOVE);
	}

	private void sendBrokerAccessRequest(final String deviceId, final AccessRequestType accessRequestType) {
		final var accessRequest = AccessRequestDto.builder()
												  .requestType(accessRequestType)
												  .imei(deviceId)
												  .build();

		outboundSender.sendDeviceAccessRequest(accessRequest);
	}
}
