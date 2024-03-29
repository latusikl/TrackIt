package pl.latusikl.trackit.trackerservice.server.services.conversion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.serializer.Deserializer;
import pl.latusikl.trackit.trackerservice.server.excpetions.DeserializationException;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class SemicolonTerminatorDeserializer implements Deserializer<String> {

	private static final char TERMINATOR = ';';
	private static final int MAX_MESSAGE_SIZE = 8192;
	private static final int AVERAGE_MESSAGE_SIZE = 95;

	@Override
	public String deserialize(final InputStream inputStream) throws IOException {
		final StringBuilder messageBuilder = new StringBuilder(AVERAGE_MESSAGE_SIZE);
		int charNumber;
		do {
			charNumber = inputStream.read();
			messageBuilder.append((char) charNumber);
			checkIfLengthNotExceeded(messageBuilder);
		} while ((char) charNumber != TERMINATOR);

		return messageBuilder.toString()
							 .trim();
	}

	private void checkIfLengthNotExceeded(final StringBuilder messageBuilder) {
		if (messageBuilder.length() == MAX_MESSAGE_SIZE) {
			throw new DeserializationException("Message serialization failed", SemicolonTerminatorDeserializer.class,
											   "Message is to long", String.format("Max message size: %s", MAX_MESSAGE_SIZE));
		}
	}
}
