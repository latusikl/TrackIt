package pl.latusikl.trackit.locationservice.locationservice.web.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class PointDto {

	private double latitude;
	private double longitude;

	@JsonValue
	public Double[] getJsonValue() {
		return new Double[] {longitude, latitude};
	}

}
