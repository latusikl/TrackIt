package pl.latusikl.trackit.locationservice.locationservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

	@NotNull
	private List<String> allowedHosts;

	@NotNull
	private String secret;

	@NotNull
	private Integer expirationTime;

}
