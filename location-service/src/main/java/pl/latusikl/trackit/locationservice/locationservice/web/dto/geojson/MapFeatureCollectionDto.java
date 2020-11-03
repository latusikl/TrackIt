package pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Value;

import java.util.Collection;

@Value
@Builder
public class MapFeatureCollectionDto {

	@Builder.Default
	private String type = "FeatureCollection";

	private Collection<JsonNode> features;
}
