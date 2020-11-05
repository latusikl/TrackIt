package pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;

import java.util.Collection;

@Value
public class MapFeatureCollectionDto {

	private String type = "FeatureCollection";

	private Collection<JsonNode> features;

	public MapFeatureCollectionDto(final Collection<JsonNode> features) {
		this.features = features;
	}
}
