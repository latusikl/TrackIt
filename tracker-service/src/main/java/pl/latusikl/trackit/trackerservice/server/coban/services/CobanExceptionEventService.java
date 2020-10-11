package pl.latusikl.trackit.trackerservice.server.coban.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.ip.tcp.connection.TcpConnectionExceptionEvent;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.trackerservice.server.coban.excpetions.DeserializationException;
import pl.latusikl.trackit.trackerservice.server.common.AbstractRuntimeException;
import pl.latusikl.trackit.trackerservice.server.common.ExceptionEventHandler;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class CobanExceptionEventService implements ExceptionEventHandler {

	private static final String TEMPLATE = "\n[%s] in {%s} \n[Message] [%s] \n[Possible cause] %s \n[Additional information] %s";
	private static final String TEMPLATE_REGULAR = "%s\n[Message] [%s]";

	@Override
	public void handleExceptionEvent(final TcpConnectionExceptionEvent exceptionEvent) {
		final Throwable cause = exceptionEvent.getCause();
		if(cause instanceof AbstractRuntimeException){
			final AbstractRuntimeException abstractRuntimeException = (AbstractRuntimeException) cause;
			if(cause instanceof DeserializationException){
				log.warn(convertToMessage(abstractRuntimeException));
			}
			else {
				log.error(convertToMessage(abstractRuntimeException));
			}
		}
		else{
			log.error(String.format(TEMPLATE_REGULAR,cause.getCause(),cause.getMessage()));
			log.debug(Arrays.toString(cause.getStackTrace()));
		}
	}

	private String convertToMessage(final AbstractRuntimeException abstractRuntimeException) {
		return String.format(TEMPLATE, abstractRuntimeException.getClass()
															   .getSimpleName(), abstractRuntimeException.getClassType(),
							 abstractRuntimeException.getMessage(), abstractRuntimeException.getPossibleCauseOfException(),
							 abstractRuntimeException.getAdditionalInformation());
	}
}
