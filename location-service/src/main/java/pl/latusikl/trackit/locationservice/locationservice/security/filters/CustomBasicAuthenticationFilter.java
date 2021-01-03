package pl.latusikl.trackit.locationservice.locationservice.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.latusikl.trackit.locationservice.locationservice.configuration.SecurityProperties;
import pl.latusikl.trackit.locationservice.locationservice.security.models.UserTokenInfo;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

/**
 Decodes token from request. If token is invalid or expired then request is rejected.
 */
@Slf4j
public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";
	private static final String EMAIL_CLAIM = "email";

	private final SecurityProperties securityProperties;

	public CustomBasicAuthenticationFilter(final AuthenticationManager authenticationManager,
										   final SecurityProperties securityProperties) {
		super(authenticationManager);
		this.securityProperties = securityProperties;
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final String authorizationHeader = getAuthorizationHeaderValue(request);

		if (isAuthorizationHeaderNonEmptyAndFormatted(authorizationHeader)) {
			final Authentication authentication = extractUserAuthenticationFromToken(request);
			SecurityContextHolder.getContext()
								 .setAuthentication(authentication);
		}

		chain.doFilter(request, response);
	}

	private String getAuthorizationHeaderValue(final HttpServletRequest request) {
		return request.getHeader(AUTHORIZATION_HEADER);
	}

	private boolean isAuthorizationHeaderNonEmptyAndFormatted(final String authorizationHeader) {
		return authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX);
	}

	private Authentication extractUserAuthenticationFromToken(final HttpServletRequest request) {
		final String authorizationHeader = getAuthorizationHeaderValue(request);
		final String jwtToken = authorizationHeader.substring(BEARER_PREFIX.length());
		try {
			final DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC512(securityProperties.getSecret()))
											 .build()
											 .verify(jwtToken);
			final UUID userId = UUID.fromString(decodedJwt.getSubject());
			final String userEmail = decodedJwt.getClaim(EMAIL_CLAIM)
											   .asString();
			return new UsernamePasswordAuthenticationToken(UserTokenInfo.of(userId, userEmail), jwtToken, Collections.emptyList());
		}
		catch (final TokenExpiredException e) {
			throw new CredentialsExpiredException("Access token expired.", e);
		}
	}

}
