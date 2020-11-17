package pl.latusikl.trackit.locationservice.locationservice.persistance.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 Column length limited to 30 chars.
 */
@AllArgsConstructor
@Getter(onMethod = @__({@JsonValue}))
public enum DeviceStatus {
	ADD_IN_PROGRESS("ADD IN PROGRESS"), CONNECTED("CONNECTED TO SYSTEM"), LIVE("LIVE"), CONNECTION_ERROR("CONNECTION ERROR"), UNKNOWN(
			"UNKNOWN");

	private final String value;
}
