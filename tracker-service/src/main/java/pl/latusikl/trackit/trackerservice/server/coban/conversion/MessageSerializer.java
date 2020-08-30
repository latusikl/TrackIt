package pl.latusikl.trackit.trackerservice.server.coban.conversion;

import org.springframework.core.serializer.Serializer;

import java.io.IOException;
import java.io.OutputStream;

public class MessageSerializer implements Serializer<String> {

	@Override
	public void serialize(final String object, final OutputStream outputStream) throws IOException {
		outputStream.write(object.getBytes());
		outputStream.flush();
	}
}
