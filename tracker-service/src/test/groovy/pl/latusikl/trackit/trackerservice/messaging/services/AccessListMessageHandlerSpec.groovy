package pl.latusikl.trackit.trackerservice.messaging.services

import pl.latusikl.trackit.trackerservice.messaging.OutboundSender
import pl.latusikl.trackit.trackerservice.messaging.dto.access.AccessRequestCallbackDto
import pl.latusikl.trackit.trackerservice.messaging.dto.access.AccessRequestDto
import pl.latusikl.trackit.trackerservice.messaging.dto.access.AccessRequestStatus
import pl.latusikl.trackit.trackerservice.messaging.dto.access.AccessRequestType
import pl.latusikl.trackit.trackerservice.persistance.repositories.ImeiRepository
import pl.latusikl.trackit.trackerservice.server.services.ConnectionRemovalService
import spock.lang.Specification
import spock.lang.Unroll

class AccessListMessageHandlerSpec extends Specification {

	def outboundSender = Mock(OutboundSender.class)
	def imeiRepository = Stub(ImeiRepository.class)
	def connectionRemovalService = Mock(ConnectionRemovalService.class)

	AccessListMessageHandler accessListMessageHandler = new AccessListMessageHandler(outboundSender, imeiRepository, connectionRemovalService)

	def 'Should return error response when request is invalid'() {
		given:
		def accessRequestType = AccessRequestType.ADD_SINGLE
		def accessRequest = invalidRequest(accessRequestType)
		def expectedResponse = errorResponse(null, 'Request is invalid. Device ID or request type is missing', accessRequestType)

		when:
		accessListMessageHandler.handleDeviceAccessRequest(accessRequest)

		then:
		1 * outboundSender.sendDeviceAccessCallback(expectedResponse)
	}

	@Unroll
	def "Should add to list when request type is: #requestType"(AccessRequestType requestType) {
		given:
		def id = "523436578323457"
		def accessRequest = validRequest(id, requestType)
		def expectedResponse = okResponse(id, requestType, 'Device was added successfully.')
		imeiRepository.saveImeiToWhitelisted(id) >> 1

		when:
		accessListMessageHandler.handleDeviceAccessRequest(accessRequest)

		then:
		1 * outboundSender.sendDeviceAccessCallback(expectedResponse)

		where:
		requestType << [AccessRequestType.ADD_SINGLE, AccessRequestType.ADD_ALL]
	}

	@Unroll
	def "Should return error response when unable to add to Redis and request is: is: #requestType"(AccessRequestType requestType) {
		given:
		def id = "523436578323457"
		def accessRequest = validRequest(id, requestType)
		def expectedResponse = errorResponse(id, 'Number of added records was not as expected\n Returned value: 0\n Possible cause: record already exists.', requestType)
		imeiRepository.saveImeiToWhitelisted(id) >> 0

		when:
		accessListMessageHandler.handleDeviceAccessRequest(accessRequest)

		then:
		1 * outboundSender.sendDeviceAccessCallback(expectedResponse)

		where:
		requestType << [AccessRequestType.ADD_SINGLE, AccessRequestType.ADD_ALL]
	}

	def "Should remove device from Redis"() {
		given:
		def id = "523436578323457"
		def accessRequest = validRequest(id, AccessRequestType.REMOVE)
		def expectedResponse = okResponse(id, AccessRequestType.REMOVE, 'Device was removed from system.')
		imeiRepository.removeImei(id) >> 1

		when:
		accessListMessageHandler.handleDeviceAccessRequest(accessRequest)

		then:
		1 * outboundSender.sendDeviceAccessCallback(expectedResponse)
		1 * connectionRemovalService.closeConnection(id)
	}

	def "Should return error when device not removed from Redis"() {
		given:
		def id = "523436578323457"
		def accessRequest = validRequest(id, AccessRequestType.REMOVE)
		def expectedResponse = errorResponse(id, 'Number of removed was not as expected.\n Returned value: 0\n Possible cause: no record with given ID exists.',AccessRequestType.REMOVE)
		imeiRepository.removeImei(id) >> 0

		when:
		accessListMessageHandler.handleDeviceAccessRequest(accessRequest)

		then:
		1 * outboundSender.sendDeviceAccessCallback(expectedResponse)
		1 * connectionRemovalService.closeConnection(id)
	}


	private AccessRequestDto invalidRequest(AccessRequestType accessRequestType) {
		return new AccessRequestDto(null, accessRequestType)
	}

	private AccessRequestDto validRequest(String id, AccessRequestType accessRequestType) {
		return new AccessRequestDto(id, accessRequestType)
	}

	private AccessRequestCallbackDto errorResponse(final String deviceId, final String message,
												   final AccessRequestType accessRequestType) {
		return AccessRequestCallbackDto.builder()
				.deviceId(deviceId)
				.accessRequestType(accessRequestType)
				.accessRequestStatus(AccessRequestStatus.ERROR)
				.requestInformation(message)
				.build()
	}

	private AccessRequestCallbackDto okResponse(final String deviceId, final AccessRequestType accessRequestType,
												final String message) {
		return AccessRequestCallbackDto.builder()
				.deviceId(deviceId)
				.accessRequestType(accessRequestType)
				.accessRequestStatus(AccessRequestStatus.FINISHED)
				.requestInformation(message)
				.build()
	}
}
