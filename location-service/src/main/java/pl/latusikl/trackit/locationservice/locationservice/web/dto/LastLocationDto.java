package pl.latusikl.trackit.locationservice.locationservice.web.dto;

import lombok.Value;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson.MapFeatureCollectionDto;

@Value(staticConstructor = "of")
public class LastLocationDto {

	private LocationDto locationData;
	private MapFeatureCollectionDto mapFeatures;
}
