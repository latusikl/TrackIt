package pl.latusikl.trackit.trackerservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.trackerservice.messaging.dto.access.AccessRequestCallbackDto;
import pl.latusikl.trackit.trackerservice.messaging.dto.access.AccessRequestDto;
import pl.latusikl.trackit.trackerservice.messaging.dto.access.AccessRequestStatus;
import pl.latusikl.trackit.trackerservice.messaging.dto.access.AccessRequestType;
import pl.latusikl.trackit.trackerservice.persistance.repositories.ImeiRepository;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableBinding({InboundProcessor.class, OutboundProcessor.class})
public class AccessListMessageHandler {

	private static final Long ADDED_NUM = 1L;
	private static final Long REMOVED_NUM = 1L;
	private static final String ADD_ERROR_MESSAGE = "Number of added records was not as expected\n Returned value: %s\n Possible cause: record already exists.";
	private static final String REMOVE_ERROR_MESSAGE = "Number of removed was not as expected.\n Returned value: %s\n Possible cause: no record with given ID exists.";

	private final OutboundSender outboundSender;
	private final ImeiRepository imeiRepository;

	@StreamListener(InboundProcessor.ACCESS_LIST_CHANNEL)
	public void handleDeviceAccessRequest(final AccessRequestDto accessRequestDto) {
		log.debug(accessRequestDto.toString());

		final AccessRequestCallbackDto accessRequestCallbackDto;
		if (isRequestValid(accessRequestDto)) {
			accessRequestCallbackDto = handleInternal(accessRequestDto.getRequestType(), accessRequestDto.getImei());
		}
		else {
			accessRequestCallbackDto = prepareErrorResponse("Request is invalid. Device ID or request type is missing");
		}
		outboundSender.sendDeviceAccessCallback(accessRequestCallbackDto);
	}

	private boolean isRequestValid(final AccessRequestDto accessRequestDto) {
		return accessRequestDto.getImei() != null && accessRequestDto.getRequestType() != null;
	}

	private AccessRequestCallbackDto handleInternal(final AccessRequestType requestType, final String deviceId) {
		final AccessRequestCallbackDto response;
		switch (requestType) {
			case ADD:
				response = addToList(deviceId);
				break;
			case REMOVE:
				response = removeFromList(deviceId);
				break;
			default:
				response = prepareErrorResponse("Unknown request type.");
				log.error("Unknown request type! Only REMOVE and ADD is allowed!");
		}
		return response;
	}

	private AccessRequestCallbackDto addToList(final String deviceId) {
		final Long numberOfAdded = imeiRepository.saveImeiToWhitelisted(deviceId);
		return numberOfAdded.equals(ADDED_NUM) ?
				prepareOkResponse() :
				prepareErrorResponse(String.format(ADD_ERROR_MESSAGE, numberOfAdded));
	}

	private AccessRequestCallbackDto removeFromList(final String deviceId) {
		final Long numberOfRemoved = imeiRepository.removeImei(deviceId);
		return numberOfRemoved.equals(REMOVED_NUM) ?
				prepareOkResponse() :
				prepareErrorResponse(String.format(REMOVE_ERROR_MESSAGE, numberOfRemoved));
	}

	private AccessRequestCallbackDto prepareOkResponse() {
		return AccessRequestCallbackDto.builder()
									   .accessRequestStatus(AccessRequestStatus.FINISHED)
									   .requestInformation("Device id was added successfully.")
									   .build();
	}

	private AccessRequestCallbackDto prepareErrorResponse(final String message) {
		return AccessRequestCallbackDto.builder()
									   .accessRequestStatus(AccessRequestStatus.ERROR)
									   .requestInformation(message)
									   .build();
	}
}
