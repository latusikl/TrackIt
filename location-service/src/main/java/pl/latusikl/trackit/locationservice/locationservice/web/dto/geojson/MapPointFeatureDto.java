package pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MapPointFeatureDto {

	@Builder.Default
	private String type = "Feature";
	private PointGeometry geometry;
	private String id;
}
