package pl.latusikl.trackit.trackerservice.server.common;

import org.springframework.integration.ip.tcp.connection.TcpConnectionExceptionEvent;

public interface ExceptionEventHandler {

	void handleExceptionEvent(TcpConnectionExceptionEvent exceptionEvent);

}
