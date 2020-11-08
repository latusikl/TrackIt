package pl.latusikl.trackit.locationservice.locationservice.security.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.latusikl.trackit.locationservice.locationservice.security.models.UserTokenInfo;

import java.util.UUID;

@Component
public class AuthenticationFacade {

	public UUID getRequestingUserId() {
		return getRequestingUserInfo().getUserId();
	}

	public String getRequestingUserEmail() {
		return getRequestingUserInfo().getUserEmail();
	}

	private UserTokenInfo getRequestingUserInfo() {
		return (UserTokenInfo) getAuthentication().getPrincipal();
	}

	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext()
									.getAuthentication();
	}
}
