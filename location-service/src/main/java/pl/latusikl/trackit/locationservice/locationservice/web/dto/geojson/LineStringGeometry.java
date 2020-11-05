package pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson;

import lombok.Value;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.PointDto;

import java.util.Collection;

@Value
public class LineStringGeometry {

	private String type = "LineString";
	private Collection<PointDto> coordinates;

	public LineStringGeometry(final Collection<PointDto> coordinates) {
		this.coordinates = coordinates;
	}
}
