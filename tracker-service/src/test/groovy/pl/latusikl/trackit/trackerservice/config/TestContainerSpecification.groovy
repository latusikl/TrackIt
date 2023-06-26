package pl.latusikl.trackit.trackerservice.config

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestContainerSpecification extends Specification {

	@Shared
	static GenericContainer redisContainer

	static final REDIS_PORT = 6379

	static {
		redisContainer = new GenericContainer<>("redis:6.0.19-alpine").withExposedPorts(REDIS_PORT).withReuse(true)
		redisContainer.start()
	}

	@DynamicPropertySource
	protected static void redisProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.redis.host", () -> redisContainer::getHost())
		registry.add("spring.redis.port", () -> redisContainer.getMappedPort(REDIS_PORT))
	}
}
