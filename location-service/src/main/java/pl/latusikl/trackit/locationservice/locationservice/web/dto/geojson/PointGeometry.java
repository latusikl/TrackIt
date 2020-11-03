package pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson;

import lombok.Builder;
import lombok.Value;

import java.util.Collection;

@Builder
@Value
public class PointGeometry {
	@Builder.Default
	String type = "Point";
	/**
	 For GeoJson point coordinates collection consist of 2 doubles.
	 */
	Collection<Double> coordinates;
}
