package pl.latusikl.trackit.locationservice.locationservice.web.dto;

import lombok.Builder;
import lombok.Value;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson.MapFeatureCollectionDto;

import java.time.LocalDateTime;

@Value
@Builder
public class LocationRangeDto {

	private LocalDateTime rangeStart;
	private LocalDateTime rangeEnd;
	private PointDto mapStart;
	private MapFeatureCollectionDto mapData;

}
