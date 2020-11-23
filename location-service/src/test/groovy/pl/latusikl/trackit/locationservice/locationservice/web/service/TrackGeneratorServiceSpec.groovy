package pl.latusikl.trackit.locationservice.locationservice.web.service

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.LocationEntity
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.LocationRepository
import pl.latusikl.trackit.locationservice.locationservice.web.dto.TrackDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime
import java.time.ZoneId

@Subject(TrackGeneratorService)
class TrackGeneratorServiceSpec extends Specification {

	static deviceId = "123345567"
	static GeometryFactory geometryFactory = new GeometryFactory()
	LocationRepository locationRepository = Stub(LocationRepository.class)
	TrackGeneratorService trackGeneratorService = new TrackGeneratorService(locationRepository)


	def 'Should return empty list when no location data returned from database'() {
		given:
		def rangeStart = LocalDateTime.now(ZoneId.of("UTC"))
		def rangeEnd = rangeStart.plusHours(2)
		locationRepository.findInRange(deviceId, rangeStart, rangeEnd) >> Collections.emptyList()

		when:
		def trackDtoList = trackGeneratorService.generateTracks(deviceId, rangeStart, rangeEnd)

		then:
		trackDtoList.size() == 0
	}

	def 'Should return track where end date is equal to beginning for single point in database'() {
		given:
		def rangeStart = LocalDateTime.now(ZoneId.of("UTC"))
		def rangeEnd = rangeStart.plusHours(2)
		def locationDate = rangeStart.plusMinutes(20)
		def singleLocation = singleLocation(locationDate)
		locationRepository.findInRange(deviceId, rangeStart, rangeEnd) >> List.of(singleLocation)

		when:
		def trackDtoList = trackGeneratorService.generateTracks(deviceId, rangeStart, rangeEnd)

		then:
		trackDtoList.size() == 1
		def track = trackDtoList.iterator().next()
		track.getStart() == locationDate
		track.getEnd() == locationDate
	}

	def 'Should throw error when range is to big'() {
		given:
		def rangeStart = LocalDateTime.now(ZoneId.of("UTC"))
		def rangeEnd = rangeStart.plusHours(5)

		when:
		trackGeneratorService.generateTracks(deviceId, rangeStart, rangeEnd)

		then:
		thrown IllegalStateException
	}

	def 'Should return single track when time breaks are smaller then 10 minutes and range smaller than two hours'() {
		given:
		def rangeStart = LocalDateTime.now(ZoneId.of("UTC"))
		def rangeEnd = rangeStart.plusHours(1)
		def startLocationDateTime = rangeStart.plusMinutes(10)
		def locationList = prepareRegularLocationList(startLocationDateTime, 4, 5);
		def lastLocationInTrackDateTime = locationList.last().getDateTimeStart()

		locationRepository.findInRange(deviceId, rangeStart, rangeEnd) >> locationList


		when:
		def trackDtoList = trackGeneratorService.generateTracks(deviceId, rangeStart, rangeEnd)

		then:
		trackDtoList.size() == 1
		def track = trackDtoList.iterator().next()
		track.getStart() == startLocationDateTime
		track.getEnd() == lastLocationInTrackDateTime

	}

	def 'Should return two tracks when data are more frequent than per 10 minutes and from more than hour'() {
		given:
		def rangeStart = LocalDateTime.now(ZoneId.of("UTC"))
		def rangeEnd = rangeStart.plusHours(1).plusMinutes(45)

		def firstTrackStart = rangeStart.plusMinutes(5)
		def locationList = prepareRegularLocationList(firstTrackStart, 15, 5)

		def firstTrackEnd = firstTrackStart.plusMinutes(60)
		def secondTrackStart = firstTrackEnd.plusMinutes(5)
		def secondTrackEnd = locationList.last().getDateTimeStart()

		locationRepository.findInRange(deviceId, rangeStart, rangeEnd) >> locationList


		when:
		def trackDtoList = trackGeneratorService.generateTracks(deviceId, rangeStart, rangeEnd) as List<TrackDto>

		then:
		trackDtoList.size() == 2
		trackDtoList.get(0).getStart() == firstTrackStart
		trackDtoList.get(0).getEnd() == firstTrackEnd
		trackDtoList.get(1).getStart() == secondTrackStart
		trackDtoList.get(1).getEnd() == secondTrackEnd
	}

	def 'Should return return three tracks when break is bigger than 10 minutes'() {
		given:
		def rangeStart = LocalDateTime.now(ZoneId.of("UTC"))
		def rangeEnd = rangeStart.plusHours(1).plusMinutes(45)

		def firstTrackStart = rangeStart.plusMinutes(10)
		def firstTrackLocationList = prepareRegularLocationList(firstTrackStart, 10, 2)
		def firstTrackEnd = firstTrackLocationList.last().getDateTimeStart()

		def secondTrackStart = firstTrackEnd.plusMinutes(20)
		def secondTrackLocationList = prepareRegularLocationList(secondTrackStart, 10, 2)
		def secondTrackEnd = secondTrackLocationList.last().getDateTimeStart()

		def thirdTrackStart = secondTrackEnd.plusMinutes(15)
		def thirdTrackLocationList = prepareRegularLocationList(thirdTrackStart, 5, 2)
		def thirdTrackEnd = thirdTrackLocationList.last().getDateTimeStart()

		def locationList = new ArrayList()
		locationList.addAll(firstTrackLocationList)
		locationList.addAll(secondTrackLocationList)
		locationList.addAll(thirdTrackLocationList)

		locationRepository.findInRange(deviceId, rangeStart, rangeEnd) >> locationList

		when:
		def trackDtoList = trackGeneratorService.generateTracks(deviceId, rangeStart, rangeEnd) as List<TrackDto>

		then:
		trackDtoList.size() == 3
		trackDtoList.get(0).getStart() == firstTrackStart
		trackDtoList.get(0).getEnd() == firstTrackEnd
		trackDtoList.get(1).getStart() == secondTrackStart
		trackDtoList.get(1).getEnd() == secondTrackEnd
		trackDtoList.get(2).getStart() == thirdTrackStart
		trackDtoList.get(2).getEnd() == thirdTrackEnd
	}

	static LocationEntity singleLocation(LocalDateTime localDateTime) {
		return LocationEntity.builder()
				.device_id(deviceId)
				.location(geometryFactory.createPoint(new Coordinate(18.1, 19.2)))
				.dateTimeStart(localDateTime)
				.build()
	}

	static List<LocationEntity> prepareRegularLocationList(LocalDateTime start, int amount, int minutesSpace) {
		List<LocationEntity> list = new ArrayList<>()
		LocalDateTime currentValue = start
		for (int i = 0; i < amount; i++) {
			list.add(singleLocation(currentValue))
			currentValue = currentValue.plusMinutes(minutesSpace)
		}
		return list
	}
}
