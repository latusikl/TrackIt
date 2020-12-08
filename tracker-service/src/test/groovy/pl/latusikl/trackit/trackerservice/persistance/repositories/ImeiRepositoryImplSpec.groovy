package pl.latusikl.trackit.trackerservice.persistance.repositories

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.latusikl.trackit.trackerservice.config.TestContainerSpecification
import spock.lang.Subject
import spock.lang.Unroll

@SpringBootTest
@Subject(ImeiRepositoryImpl.class)
class ImeiRepositoryImplSpec extends TestContainerSpecification {

	@Autowired
	ImeiRepositoryImpl imeiRepository

	static final IMEI = '446303941458077'
	static final UUID = '9b7a1033-311e-4b0f-801f-139e5f586d8a'


	@Unroll
	def 'Should save #value to Redis'(String value) {
		when:
		def amountOfSaved = imeiRepository.saveImeiToWhitelisted(value)

		then:
		amountOfSaved == 1

		where:
		value << [IMEI,UUID]
	}

	def 'Should return true if id is whitelisted'(){
		given:
		def id = '456303941458079'
		imeiRepository.saveImeiToWhitelisted(id)

		when:
		def isWhitelisted = imeiRepository.isImeiWhitelisted(id)

		then:
		isWhitelisted == true
	}

	def 'Should return false if id is whitelisted'(){
		given:
		def id = '9b7a1033-311e-4b0f-801f-133e5f586c7a'

		when:
		def isWhitelisted = imeiRepository.isImeiWhitelisted(id)

		then:
		isWhitelisted == false
	}

	def 'Should remove saved id'(){
		given:
		def id = '456303941458079'
		imeiRepository.saveImeiToWhitelisted(id)

		when:
		def amountOfRemoved = imeiRepository.removeImei(id)

		then:
		amountOfRemoved == 1
	}
}
