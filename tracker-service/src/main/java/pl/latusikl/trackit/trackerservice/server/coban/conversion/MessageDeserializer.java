package pl.latusikl.trackit.trackerservice.server.coban.conversion;

import org.springframework.core.serializer.Deserializer;

import java.io.IOException;
import java.io.InputStream;

public class MessageDeserializer implements Deserializer<String> {

	private static final char TERMINATOR = ';';

	@Override
	public String deserialize(final InputStream inputStream) throws IOException {
		final StringBuilder stringBuilder = new StringBuilder();
		int charNumber;
		do {
			charNumber = inputStream.read();
			stringBuilder.append((char) charNumber);
		} while ((char) charNumber != TERMINATOR);
		return stringBuilder.toString();
	}
}
