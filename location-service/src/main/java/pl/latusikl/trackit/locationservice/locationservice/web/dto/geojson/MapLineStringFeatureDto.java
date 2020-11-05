package pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MapLineStringFeatureDto {

	private String type = "Feature";
	private LineStringGeometry geometry;
	private String id;

	@Builder
	public MapLineStringFeatureDto(final LineStringGeometry geometry, final String id) {
		this.geometry = geometry;
		this.id = id;
	}
}
