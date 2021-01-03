package pl.latusikl.trackit.trackerservice.persistance

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import pl.latusikl.trackit.trackerservice.config.TestContainerSpecification
import pl.latusikl.trackit.trackerservice.messaging.OutboundSender
import pl.latusikl.trackit.trackerservice.persistance.repositories.ImeiRepositoryImpl

class RedisCleanerIntegrationSpec extends TestContainerSpecification {

	@SpringBean
	OutboundSender outboundSender = Mock()

	@Autowired
	RedisCleaner redisCleaner

	@Autowired
	ImeiRepositoryImpl imeiRepository

	def 'Should clean saved ids in database'(){
		given:
		def id1='512267787552492'
		def id2 ='ad71fe89-55d7-4466-b59b-1fd4cf0be2ec'
		def amountOfSaved = imeiRepository.saveImeiToWhitelisted(id1,id2)

		when:
		redisCleaner.executeRefreshProcess()

		then:
		amountOfSaved == 2
		imeiRepository.isImeiWhitelisted(id1) == false
		imeiRepository.isImeiWhitelisted(id2) == false
		1 * outboundSender.sendAllAllowedDevicesRequest()
	}

}
