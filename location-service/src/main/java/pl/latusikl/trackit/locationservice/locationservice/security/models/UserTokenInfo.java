package pl.latusikl.trackit.locationservice.locationservice.security.models;

import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class UserTokenInfo {

	private UUID userId;
	private String userEmail;

}
