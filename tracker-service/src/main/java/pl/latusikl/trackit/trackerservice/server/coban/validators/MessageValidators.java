package pl.latusikl.trackit.trackerservice.server.coban.validators;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

@Slf4j
public class MessageValidators {
	private static final Predicate<String> loginMessageValidatorObj = new LoginMessageValidator();

	private static final Predicate<String> locationMessageValidatorObj = new LocationMessageValidator();

	private MessageValidators() {
		//noop
	}

	public static Predicate<String> loginMessageValidator() {
		return loginMessageValidatorObj;
	}

	public static boolean validateLoginMessage(final String message) {
		return loginMessageValidatorObj.test(message);
	}

	public static Predicate<String> locationMessageValidator() {
		return locationMessageValidatorObj;
	}

	public static boolean validateLocationMessage(final String message) {
		return locationMessageValidatorObj.test(message);
	}

}
