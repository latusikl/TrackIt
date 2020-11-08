package pl.latusikl.trackit.locationservice.locationservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.util.MimeTypeUtils;
import pl.latusikl.trackit.locationservice.locationservice.configuration.SecurityProperties;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.UserData;
import pl.latusikl.trackit.locationservice.locationservice.security.models.LoginDto;
import pl.latusikl.trackit.locationservice.locationservice.security.models.LoginResponseDto;
import pl.latusikl.trackit.locationservice.locationservice.security.models.UserDetailsImpl;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

@RequiredArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private static final String CONTENT_HEADER = "Content-Type";
	private static final String EMAIL_CLAIM = "email";
	private final ObjectMapper objectMapper;
	private final SecurityProperties securityProperties;
	private final AuthenticationManager authenticationManager;

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
			throws AuthenticationException {
		try {
			final var loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
			final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					loginDto.getEmail(), loginDto.getPassword());
			return authenticationManager.authenticate(authenticationToken);
		}
		catch (final IOException e) {
			throw new PreAuthenticatedCredentialsNotFoundException("Invalid request body.");
		}
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
											final FilterChain chain, final Authentication authResult) throws IOException {
		final UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
		final var loginResponseDto = createTokenAndAddToTokenHolder(userDetails.getUserData());

		addTokenToResponse(loginResponseDto, response);

	}

	private LoginResponseDto createTokenAndAddToTokenHolder(final UserData userData) {
		final Date expirationDate = new Date(System.currentTimeMillis() + securityProperties.getExpirationTime());
		final String token = JWT.create()
								.withSubject(userData.getUserId()
													 .toString())
								.withClaim(EMAIL_CLAIM, userData.getUserEmail())
								.withExpiresAt(expirationDate)
								.sign(Algorithm.HMAC256(securityProperties.getSecret()));
		return LoginResponseDto.of(expirationDate.toInstant()
												 .atZone(ZoneId.of("UTC"))
												 .toLocalDateTime(), token);
	}

	private void addTokenToResponse(final LoginResponseDto loginResponseDto, final HttpServletResponse response) throws IOException {
		response.addHeader(CONTENT_HEADER, MimeTypeUtils.APPLICATION_JSON_VALUE);
		response.getWriter()
				.write(objectMapper.writeValueAsString(loginResponseDto));
	}
}
