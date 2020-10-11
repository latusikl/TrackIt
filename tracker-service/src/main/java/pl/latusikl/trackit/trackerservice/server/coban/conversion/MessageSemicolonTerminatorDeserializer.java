package pl.latusikl.trackit.trackerservice.server.coban.conversion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.serializer.Deserializer;
import pl.latusikl.trackit.trackerservice.server.coban.excpetions.DeserializationException;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class MessageSemicolonTerminatorDeserializer implements Deserializer<String> {

	private static final char TERMINATOR = ';';
	private static final int MAX_MESSAGE_SIZE = 2048;
	private static final int AVERAGE_MESSAGE_SIZE = 95;

	//TODO CRLF only for tries with telnet remove or make switch
	@Override
	public String deserialize(final InputStream inputStream) throws IOException {
		final StringBuilder messageBuilder = new StringBuilder(AVERAGE_MESSAGE_SIZE);
		int charNumber;
		do {
			charNumber = inputStream.read();
			checkIfReadable(charNumber);
			messageBuilder.append((char) charNumber);
			checkIfLengthNotExceeded(messageBuilder);
		}
		while ((char) charNumber != TERMINATOR);

		return messageBuilder.toString().trim();
	}

	private void checkIfReadable(final int charNumber) {
		if (charNumber < 0) {
			throw new DeserializationException("Unable to finish message serialization.", MessageSemicolonTerminatorDeserializer.class,
					"Socket was closed during deserialization", String.format("Received byte with number: %s", charNumber));
		}
	}

	private void checkIfLengthNotExceeded(final StringBuilder messageBuilder) {
		if (messageBuilder.length() == MAX_MESSAGE_SIZE) {
			throw new DeserializationException(
					String.format("Message serialization failed - message is to long. Max message size: %s", MAX_MESSAGE_SIZE),
					MessageSemicolonTerminatorDeserializer.class);
		}
	}
}
