package pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson;

import lombok.Value;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.PointDto;

@Value
public class PointGeometry {

	private String type = "Point";
	private PointDto coordinates;

	public PointGeometry(final PointDto coordinates) {
		this.coordinates = coordinates;
	}
}
