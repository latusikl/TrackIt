package pl.latusikl.trackit.locationservice.locationservice.messaging.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.locationservice.locationservice.messaging.OutboundSender;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestDto;
import pl.latusikl.trackit.locationservice.locationservice.messaging.dto.access.AccessRequestType;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.UserDevice;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.UserDeviceRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AllAllowedSendingService {

	private static final int PAGE_SIZE = 50;
	private final UserDeviceRepository userDeviceRepository;
	private final OutboundSender outboundSender;

	@Async
	public void sendAllAllowedDevicesAccessRequest() {
		final int totalPages = Math.floorDiv(userDeviceRepository.countAll() , PAGE_SIZE);
		for (int i = 0; i <= totalPages; i++) {
			final Page<UserDevice> userDevicePage = userDeviceRepository.findAll(PageRequest.of(i, PAGE_SIZE));
			userDevicePage.stream()
						  .map(this::mapValuesToRequest)
						  .forEach(outboundSender::sendDeviceAccessRequest);
		}
	}

	private AccessRequestDto mapValuesToRequest(final UserDevice userDevice) {
		return AccessRequestDto.builder()
							   .imei(userDevice.getDeviceId())
							   .requestType(AccessRequestType.ADD_ALL)
							   .build();
	}
}
