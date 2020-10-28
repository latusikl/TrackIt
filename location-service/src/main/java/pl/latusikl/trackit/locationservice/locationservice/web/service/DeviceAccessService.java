package pl.latusikl.trackit.locationservice.locationservice.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.locationservice.locationservice.messaging.OutboundSender;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestDto;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestType;
import pl.latusikl.trackit.locationservice.locationservice.persistance.converters.DeviceAccessConverter;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.UserDeviceRepository;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.DeviceAccessDto;

@Service
@RequiredArgsConstructor
public class DeviceAccessService {

	private final UserDeviceRepository userDevicesRepository;
	private final DeviceAccessConverter deviceAccessConverter;
	private final OutboundSender outboundSender;

	public void sendActivateAccessRequest(final DeviceAccessDto deviceAccessDto) {
		final var userDevice = deviceAccessConverter.convert(deviceAccessDto);
		userDevicesRepository.save(userDevice);

		sendBrokerAccessRequest(deviceAccessDto.getDeviceId(), AccessRequestType.ADD);
	}

	public void sendRevokeAccessRequest(final String deviceId) {
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
