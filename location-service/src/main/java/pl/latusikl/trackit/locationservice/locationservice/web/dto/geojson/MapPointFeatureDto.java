package pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson;

import lombok.Builder;
import lombok.Value;

@Value
public class MapPointFeatureDto {

	private String type = "Feature";
	private PointGeometry geometry;
	private String id;

	@Builder
	public MapPointFeatureDto(final PointGeometry geometry, final String id) {
		this.geometry = geometry;
		this.id = id;
	}
}
