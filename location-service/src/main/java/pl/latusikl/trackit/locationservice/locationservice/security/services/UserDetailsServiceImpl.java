package pl.latusikl.trackit.locationservice.locationservice.security.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.locationservice.locationservice.persistance.repository.UserDataRepository;
import pl.latusikl.trackit.locationservice.locationservice.security.models.UserDetailsImpl;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final String USER_EXCEPTION_FORMAT = "User with email %s doesn't exist";
	final UserDataRepository userDataRepository;

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		return userDataRepository.findByUserEmail(email)
								 .map(UserDetailsImpl::new)
								 .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_EXCEPTION_FORMAT, email)));
	}
}
