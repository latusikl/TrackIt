package pl.latusikl.trackit.locationservice.locationservice.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson.MapPointFeatureDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson.PointGeometry;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapFeatureCreator {

	private final UuidGenerator uuidGenerator;
	private final ObjectMapper objectMapper;

	JsonNode createPoint(final double latitude, final double longitude) {
		final var pointGeometry = PointGeometry.builder()
											   .coordinates(List.of(longitude, latitude))
											   .build();
		final var pointFeatureDto = MapPointFeatureDto.builder()
													  .geometry(pointGeometry)
													  .id(uuidGenerator.randomUuid())
													  .build();
		return objectMapper.valueToTree(pointFeatureDto);
	}

}
