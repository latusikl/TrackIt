package pl.latusikl.trackit.locationservice.locationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.locationservice.locationservice.messaging.OutboundSender;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestDto;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestType;

@Service
@RequiredArgsConstructor
public class DeviceAccessService {

	private final OutboundSender outboundSender;

	public void sendActivateAccessRequest(final String deviceId) {
		final var accessRequest = AccessRequestDto.builder()
												  .requestType(AccessRequestType.ADD)
												  .imei(deviceId)
												  .build();
		outboundSender.sendDeviceAccessRequest(accessRequest);
	}

	public void sendRevokeAccessRequest(final String deviceId) {
		final var accessRequest = AccessRequestDto.builder()
												  .requestType(AccessRequestType.REMOVE)
												  .imei(deviceId)
												  .build();

		outboundSender.sendDeviceAccessRequest(accessRequest);
	}
}
