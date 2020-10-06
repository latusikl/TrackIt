package pl.latusikl.trackit.trackerservice.server.coban.conversion;

import org.springframework.core.serializer.Deserializer;

import java.io.IOException;
import java.io.InputStream;

public class MessageDeserializer implements Deserializer<String> {

	private static final char TERMINATOR = ';';
	private static final int CR = 13;
	private static final int LF = 10;

	//TODO CRLF only for tries with telnet remove or make switch
	@Override
	public String deserialize(final InputStream inputStream) throws IOException {
		final StringBuilder stringBuilder = new StringBuilder();
		int charNumber;
		do {
			charNumber = inputStream.read();
			if (!isForRemoval(charNumber)) {
				stringBuilder.append((char) charNumber);
			}
		} while ((char) charNumber != TERMINATOR);
		return stringBuilder.toString();
	}

	private boolean isForRemoval(final int charNumber) {
		return charNumber == CR || charNumber == LF;
	}
}
