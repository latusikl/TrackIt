package pl.latusikl.trackit.locationservice.locationservice.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.PointDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson.LineStringGeometry;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson.MapLineStringFeatureDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson.MapPointFeatureDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson.PointGeometry;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MapFeatureCreator {

	private final UuidGenerator uuidGenerator;
	private final ObjectMapper objectMapper;

	JsonNode createPoint(final double latitude, final double longitude) {
		final var pointFeatureDto = MapPointFeatureDto.builder()
													  .geometry(new PointGeometry(PointDto.of(latitude, longitude)))
													  .id(uuidGenerator.randomUuid())
													  .build();
		return objectMapper.valueToTree(pointFeatureDto);
	}

	JsonNode createLineString(final Collection<PointDto> pointDtoCollection) {
		final var lineStringDto = MapLineStringFeatureDto.builder()
														 .geometry(new LineStringGeometry(pointDtoCollection))
														 .id(uuidGenerator.randomUuid())
														 .build();
		return objectMapper.valueToTree(lineStringDto);
	}

}
